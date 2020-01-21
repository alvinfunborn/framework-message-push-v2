package com.alvin.framework.message.push.v2.substance.pusher;

import com.alvin.framework.message.push.v2.substance.executor.Executor;
import com.alvin.framework.message.push.v2.substance.lock.AbstractPushLock;
import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.queue.AbstractClusterMessageQueue;
import com.alvin.framework.message.push.v2.substance.trigger.ScheduleTrigger;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractStatefulTunnel;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;

import java.time.LocalDateTime;

/**
 * datetime 2020/1/18 17:17
 *
 * @author zhouwenxiang
 */
public abstract class AbstractSingleMessageQueuePusher implements SingleMessageQueuePusher {

    protected AbstractTunnel tunnel;
    protected AbstractClusterMessageQueue queue;
    protected Executor executor;
    protected AbstractPushLock lock;

    public AbstractSingleMessageQueuePusher(AbstractTunnel tunnel,
                                            AbstractClusterMessageQueue queue,
                                            Executor executor,
                                            AbstractPushLock lock) {
        this.tunnel = tunnel;
        this.queue = queue;
        this.executor = executor;
        this.lock = lock;
    }

    @Override
    public void start() {
        doStart();
    }

    private void doStart() {
        try {
            if (lock.tryLock()) {
                executor.execute(this::pushContinuously, true);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void add(Message message, boolean head) {
        queue.add(message, head);
        start();
    }

    @Override
    public void reportReceipt(String id) {
        queue.reportReceipt(id);
    }

    @Override
    public void pushContinuously() {
        while (true) {
            Message message = pop();
            if (message == null) {
                break;
            }
            if (!pushContinuously(message)) {
                break;
            }
        }
    }

    protected abstract Message pop();

    protected TunnelTip push(Message message) {
        TunnelTip tunnelTip = pushByStateful(message);
        if (message.getPolicy().getTunnelPolicy().isDuplex() && tunnelTip.isOk()) {
            // wait for receipt
            tunnelTip = waitForReceipt(message);
        }
        return tunnelTip;
    }

    protected TunnelTip waitForReceipt(Message message) {
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

    protected TunnelTip pushByStateful(Message message) {
        if (message.getPolicy().getTunnelPolicy().isStateful()) {
            return pushWhenConnected(message);
        } else {
            return doPush(message);
        }
    }

    protected TunnelTip pushWhenConnected(Message message) {
        if (tunnel instanceof AbstractStatefulTunnel) {
            return doPushWhenConnected(message);
        }
        return doPush(message);
    }

    protected TunnelTip doPushWhenConnected(Message message) {
        return ((AbstractStatefulTunnel) tunnel).pushWhenConnected(message.getData());
    }

    protected TunnelTip doPush(Message message) {
        return tunnel.push(message.getData());
    }

    protected void markTried(Message message) {
        message.markTried();
    }

    protected void preRetry(Message message, TunnelTip tunnelTip) {
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
