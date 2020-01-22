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

/**
 * datetime 2020/1/22 15:50
 *
 * @author zhouwenxiang
 */
@EqualsAndHashCode(callSuper = true)
public class GeneralMessageTunnelPusher extends AbstractTunnelPusher {

    public GeneralMessageTunnelPusher(Receiver receiver, AbstractWrappedTunnel tunnel, WrappedMessageQueue queue, WrappedPushRecorder recorder, WrappedPushLock lock, Executor executor) {
        super(receiver, tunnel, queue, recorder, lock, executor);
    }

    @Override
    protected WrappedMessage pop() {
        return queue.popGeneralMessage(receiver, tunnel);
    }

    @Override
    public boolean pushContinuously(WrappedMessage message) {
        PushTrigger trigger = message.getPolicy().getTrigger();
        if (trigger instanceof ScheduleTrigger) {
            executor.executeOnSchedule(() -> postPush(message), ((ScheduleTrigger) trigger).getSchedule());
            return true;
        } else {
            executor.execute(() -> postPush(message));
            return true;
        }
    }

    @Override
    public boolean postPush(WrappedMessage message) {
        TunnelTip tunnelTip = push(message);
        markTried(message);
        recorder.recordAttempt(message);
        if (tunnelTip.isOk()) {
            recorder.recordSuccess(message);
        } else {
            if (message.retryable()) {
                preRetry(message, tunnelTip);
                queue.add(receiver, message, tunnel, true);
            }
            recorder.recordError(message, tunnelTip);
        }
        return true;
    }
}
