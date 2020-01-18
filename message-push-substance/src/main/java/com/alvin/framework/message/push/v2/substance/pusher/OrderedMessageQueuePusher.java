package com.alvin.framework.message.push.v2.substance.pusher;

import com.alvin.framework.message.push.v2.substance.executor.Executor;
import com.alvin.framework.message.push.v2.substance.lock.AbstractPushLock;
import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.queue.AbstractMessageQueue;
import com.alvin.framework.message.push.v2.substance.trigger.PushTrigger;
import com.alvin.framework.message.push.v2.substance.trigger.ScheduleTrigger;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * datetime 2020/1/18 17:19
 *
 * @author zhouwenxiang
 */
public class OrderedMessageQueuePusher extends AbstractSingleMessageQueuePusher {

    public OrderedMessageQueuePusher(AbstractTunnel tunnel, AbstractMessageQueue queue, Executor executor, AbstractPushLock lock) {
        super(tunnel, queue, executor, lock);
    }

    @Override
    public boolean pushContinuously(Message message) {
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
    public boolean postPush(Message message) {
        TunnelTip tunnelTip = push(message);
        if (tunnelTip.isOk()) {
            markTried(message);
            queue.onPushAttempt(message);
            queue.onPushOk(message);
            return true;
        } else if (tunnelTip.isNotConnected() && message.getPolicy().getTunnelPolicy().isStateful()) {
            queue.add(message, true);
            return false;
        } else {
            markTried(message);
            queue.onPushAttempt(message);
            if (message.getPolicy().getRetryPolicy() != null && message.getPolicy().getRetryPolicy().getRetry() >= message.getTryTimes().get()) {
                preRetry(message, tunnelTip);
                return pushContinuously(message);
            }
            queue.onPushError(message, tunnelTip);
            return true;
        }
    }
}
