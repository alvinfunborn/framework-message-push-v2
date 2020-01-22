package com.alvin.framework.message.push.v2.wrapper.pusher;

import com.alvin.framework.message.push.v2.substance.executor.Executor;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.trigger.ScheduleTrigger;
import com.alvin.framework.message.push.v2.wrapper.lock.WrappedPushLock;
import com.alvin.framework.message.push.v2.wrapper.model.Pusher;
import com.alvin.framework.message.push.v2.wrapper.model.WrappedMessage;
import com.alvin.framework.message.push.v2.wrapper.queue.WrappedMessageQueue;
import com.alvin.framework.message.push.v2.wrapper.receiver.Receiver;
import com.alvin.framework.message.push.v2.wrapper.recorder.WrappedPushRecorder;
import com.alvin.framework.message.push.v2.wrapper.tunnel.AbstractWrappedStatefulTunnel;
import com.alvin.framework.message.push.v2.wrapper.tunnel.AbstractWrappedTunnel;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * datetime 2020/1/22 14:08
 *
 * @author zhouwenxiang
 */
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractTunnelPusher extends Pusher implements TunnelPusher {

    protected WrappedMessageQueue queue;
    protected WrappedPushRecorder recorder;
    protected WrappedPushLock lock;
    protected Executor executor;

    public AbstractTunnelPusher(Receiver receiver, AbstractWrappedTunnel tunnel, WrappedMessageQueue queue, WrappedPushRecorder recorder, WrappedPushLock lock, Executor executor) {
        super(receiver, tunnel);
        this.queue = queue;
        this.recorder = recorder;
        this.lock = lock;
        this.executor = executor;
    }

    @Override
    public void start() {
        executor.execute(this::pushContinuously, true);
    }

    @Override
    public void add(WrappedMessage message, boolean head) {
        queue.add(receiver, message, tunnel, head);
        start();
    }

    @Override
    public void reportReceipt(String id) {
        queue.reportReceipt(id);
    }

    @Override
    public void pushContinuously() {
        boolean getLock = false;
        try {
            getLock = lock.tryLock(receiver, tunnel);
            if (getLock) {
                while (true) {
                    WrappedMessage message = pop();
                    if (message == null) {
                        break;
                    }
                    if (!pushContinuously(message)) {
                        break;
                    }
                }
            }
        } finally {
            if (getLock) {
                lock.unlock(receiver, tunnel);
            }
        }
    }

    protected abstract WrappedMessage pop();

    protected TunnelTip push(WrappedMessage message) {
        TunnelTip tunnelTip = pushByStateful(message);
        if (message.getPolicy().getTunnelPolicy().isDuplex() && tunnelTip.isOk()) {
            // wait for receipt
            tunnelTip = waitForReceipt(message);
        }
        return tunnelTip;
    }

    protected TunnelTip waitForReceipt(WrappedMessage message) {
        String id = message.getId();
        long timeout = message.getPolicy().getTunnelPolicy().getTimeoutMills();
        long start = System.currentTimeMillis();
        for (long now = start, step = 100; now - start < timeout; step *= 1.1, now += step) { // todo 优化等待receipt
            if (queue.consumeReceipt(id)) {
                // find receipt
                return TunnelTip.ok();
            }
        }
        return TunnelTip.noReceipt();
    }

    protected TunnelTip pushByStateful(WrappedMessage message) {
        if (message.getPolicy().getTunnelPolicy().isStateful()) {
            return pushWhenConnected(message);
        } else {
            return doPush(receiver, message);
        }
    }

    protected TunnelTip pushWhenConnected(WrappedMessage message) {
        if (tunnel instanceof AbstractWrappedStatefulTunnel) {
            return doPushWhenConnected(message);
        }
        return doPush(receiver, message);
    }

    protected TunnelTip doPushWhenConnected(WrappedMessage message) {
        return ((AbstractWrappedStatefulTunnel) tunnel).pushWhenConnected(receiver, message);
    }

    protected TunnelTip doPush(Receiver receiver, WrappedMessage message) {
        return tunnel.push(receiver, message);
    }

    protected void markTried(WrappedMessage message) {
        message.markTried();
    }

    protected void preRetry(WrappedMessage message, TunnelTip tunnelTip) {
        message.getPolicy().setTunnelPolicy(message.getPolicy().getRetryPolicy().getTunnelPolicy());
        // todo retry delay factory, retry max times;
        if (message.getPolicy().getRetryPolicy().isFollowSuggestiong() && validSuggestTime(tunnelTip.getSuggestTime())) {
            message.getPolicy().setTrigger(ScheduleTrigger.at(tunnelTip.getSuggestTime()));
        } else {
            message.getPolicy().setTrigger(message.getPolicy().getRetryPolicy().getTrigger());
        }
    }

    protected boolean validSuggestTime(LocalDateTime time) {
        return time != null && time.isAfter(LocalDateTime.now());
    }
}
