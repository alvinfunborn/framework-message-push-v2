package com.alvin.framework.message.push.v2.network.pusher;

import com.alvin.framework.message.push.v2.substance.executor.Executor;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.trigger.PushTrigger;
import com.alvin.framework.message.push.v2.substance.trigger.ScheduleTrigger;
import com.alvin.framework.message.push.v2.network.lock.WrappedPushLock;
import com.alvin.framework.message.push.v2.network.model.WrappedMessage;
import com.alvin.framework.message.push.v2.network.queue.WrappedMessageQueue;
import com.alvin.framework.message.push.v2.network.receiver.Receiver;
import com.alvin.framework.message.push.v2.network.recorder.WrappedPushRecorder;
import com.alvin.framework.message.push.v2.network.tunnel.AbstractWrappedTunnel;
import lombok.EqualsAndHashCode;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * datetime 2020/1/22 15:19
 *
 * @author zhouwenxiang
 */
@EqualsAndHashCode(callSuper = true)
public class OrderedMessageTunnelPusher extends AbstractTunnelPusher {

    public OrderedMessageTunnelPusher(Receiver receiver, AbstractWrappedTunnel tunnel, WrappedMessageQueue queue, WrappedPushRecorder recorder, WrappedPushLock lock, Executor executor) {
        super(receiver, tunnel, queue, recorder, lock, executor);
    }

    @Override
    protected WrappedMessage pop() {
        return queue.popOrderedMessage(receiver, tunnel);
    }

    @Override
    public boolean pushContinuously(WrappedMessage message) {
        PushTrigger trigger = message.getPolicy().getTrigger();
        if (trigger instanceof ScheduleTrigger) {
            try { // todo 优化定时有序消息
                Thread.sleep(Duration.between(LocalDateTime.now(), ((ScheduleTrigger) trigger).getSchedule()).toMillis());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return postPush(message);
    }

    @Override
    public boolean postPush(WrappedMessage message) {
        TunnelTip tunnelTip = push(message);
        if (tunnelTip.isOk()) {
            markTried(message);
            recorder.recordAttempt(message);
            recorder.recordSuccess(message);
            return true;
        } else if (tunnelTip.isNotConnected() && message.getPolicy().getTunnelPolicy().isStateful()) {
            queue.add(receiver, message, tunnel, true);
            return false;
        } else {
            markTried(message);
            recorder.recordAttempt(message);
            if (message.retryable()) {
                preRetry(message, tunnelTip);
                return pushContinuously(message);
            }
            recorder.recordError(message, tunnelTip);
            return true;
        }
    }

}
