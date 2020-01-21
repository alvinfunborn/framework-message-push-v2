package com.alvin.framework.message.push.v2.substance.pusher;

import com.alvin.framework.message.push.v2.substance.executor.Executor;
import com.alvin.framework.message.push.v2.substance.lock.AbstractPushLock;
import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.queue.AbstractClusterMessageQueue;
import com.alvin.framework.message.push.v2.substance.trigger.PushTrigger;
import com.alvin.framework.message.push.v2.substance.trigger.ScheduleTrigger;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;

/**
 * datetime 2020/1/18 17:23
 *
 * @author zhouwenxiang
 */
public class GeneralMessageQueuePusher extends AbstractSingleMessageQueuePusher {

    public GeneralMessageQueuePusher(AbstractTunnel tunnel, AbstractClusterMessageQueue queue, Executor executor, AbstractPushLock lock) {
        super(tunnel, queue, executor, lock);
    }

    @Override
    public boolean pushContinuously(Message message) {
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
    public boolean postPush(Message message) {
        TunnelTip tunnelTip = push(message);
        markTried(message);
        queue.onPushAttempt(message);
        if (tunnelTip.isOk()) {
            queue.onPushOk(message);
        } else {
            if (message.getPolicy().getRetryPolicy() != null && message.getPolicy().getRetryPolicy().getRetry() >= message.getTryTimes().get()) {
                preRetry(message, tunnelTip);
                queue.add(message, true);
            }
            queue.onPushError(message, tunnelTip);
        }
        return true;
    }

    @Override
    protected Message pop() {
        return queue.popGeneralMessage();
    }
}
