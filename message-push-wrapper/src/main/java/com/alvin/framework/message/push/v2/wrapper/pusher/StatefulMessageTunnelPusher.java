package com.alvin.framework.message.push.v2.wrapper.pusher;

import com.alvin.framework.message.push.v2.substance.executor.Executor;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.trigger.PushTrigger;
import com.alvin.framework.message.push.v2.substance.trigger.ScheduleTrigger;
import com.alvin.framework.message.push.v2.wrapper.lock.WrappedPushLock;
import com.alvin.framework.message.push.v2.wrapper.model.WrappedMessage;
import com.alvin.framework.message.push.v2.wrapper.queue.WrappedMessageQueue;
import com.alvin.framework.message.push.v2.wrapper.receiver.Receiver;
import com.alvin.framework.message.push.v2.wrapper.recorder.WrappedPushRecorder;
import com.alvin.framework.message.push.v2.wrapper.tunnel.AbstractWrappedStatefulTunnel;
import com.alvin.framework.message.push.v2.wrapper.tunnel.AbstractWrappedTunnel;
import lombok.EqualsAndHashCode;

/**
 * datetime 2020/1/22 15:48
 *
 * @author zhouwenxiang
 */
@EqualsAndHashCode(callSuper = true)
public class StatefulMessageTunnelPusher extends AbstractTunnelPusher {

    public StatefulMessageTunnelPusher(Receiver receiver, AbstractWrappedTunnel tunnel, WrappedMessageQueue queue, WrappedPushRecorder recorder, WrappedPushLock lock, Executor executor) {
        super(receiver, tunnel, queue, recorder, lock, executor);
    }

    @Override
    protected WrappedMessage pop() {
        return queue.popStatefulMessage(receiver, tunnel);
    }

    @Override
    public boolean pushContinuously(WrappedMessage message) {
        PushTrigger trigger = message.getPolicy().getTrigger();
        if (trigger instanceof ScheduleTrigger) {
            executor.executeOnSchedule(() -> postPush(message), ((ScheduleTrigger) trigger).getSchedule());
            return true;
        } else {
            if (tunnel instanceof AbstractWrappedStatefulTunnel) {
                if (((AbstractWrappedStatefulTunnel) tunnel).connected(receiver)) {
                    executor.execute(() -> postPush(message));
                    return true;
                } else {
                    return false;
                }
            } else {
                executor.execute(() -> postPush(message));
                return true;
            }
        }
    }

    @Override
    public boolean postPush(WrappedMessage message) {
        TunnelTip tunnelTip = push(message);
        if (tunnelTip.isOk()) {
            markTried(message);
            recorder.recordAttempt(message);
            recorder.recordSuccess(message);
        } else if (tunnelTip.isNotConnected()) {
            queue.add(receiver, message, tunnel, true);
        } else {
            markTried(message);
            recorder.recordAttempt(message);
            if (message.retryable()) {
                preRetry(message, tunnelTip);
                queue.add(receiver, message, tunnel, true);
            }
            recorder.recordError(message, tunnelTip);
        }
        return true;
    }
}
