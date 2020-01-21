package com.alvin.framework.message.push.v2.substance.pusher;

import com.alvin.framework.message.push.v2.substance.executor.Executor;
import com.alvin.framework.message.push.v2.substance.lock.AbstractPushLock;
import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.queue.AbstractClusterMessageQueue;
import com.alvin.framework.message.push.v2.substance.trigger.PushTrigger;
import com.alvin.framework.message.push.v2.substance.trigger.ScheduleTrigger;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractStatefulTunnel;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;

/**
 * datetime 2020/1/18 17:21
 *
 * @author zhouwenxiang
 */
public class StatefulMessageQueuePusher extends AbstractSingleMessageQueuePusher {

    public StatefulMessageQueuePusher(AbstractTunnel tunnel, AbstractClusterMessageQueue queue, Executor executor, AbstractPushLock lock) {
        super(tunnel, queue, executor, lock);
    }

    @Override
    public boolean pushContinuously(Message message) {
        PushTrigger trigger = message.getPolicy().getTrigger();
        if (trigger instanceof ScheduleTrigger) {
            executor.executeOnSchedule(() -> postPush(message), ((ScheduleTrigger) trigger).getSchedule());
            return true;
        } else {
            if (tunnel instanceof AbstractStatefulTunnel) {
                if (((AbstractStatefulTunnel) tunnel).connected()) {
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
    public boolean postPush(Message message) {
        TunnelTip tunnelTip = push(message);
        if (tunnelTip.isOk()) {
            markTried(message);
            queue.onPushAttempt(message);
            queue.onPushOk(message);
        } else if (tunnelTip.isNotConnected()) {
            queue.add(message, true);
        } else {
            markTried(message);
            queue.onPushAttempt(message);
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
        return queue.popStatefulMessage();
    }
}
