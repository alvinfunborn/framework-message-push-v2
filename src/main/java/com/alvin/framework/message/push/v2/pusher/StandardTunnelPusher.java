package com.alvin.framework.message.push.v2.pusher;

import com.alvin.framework.message.push.v2.executor.Executor;
import com.alvin.framework.message.push.v2.model.Message;
import com.alvin.framework.message.push.v2.model.TunnelTip;
import com.alvin.framework.message.push.v2.model.enums.TunnelTipCodeEnum;
import com.alvin.framework.message.push.v2.policy.PushPolicy;
import com.alvin.framework.message.push.v2.policy.PushTunnelPolicy;
import com.alvin.framework.message.push.v2.queue.AbstractMessageQueue;
import com.alvin.framework.message.push.v2.trigger.DelayTrigger;
import com.alvin.framework.message.push.v2.trigger.InstantlyTrigger;
import com.alvin.framework.message.push.v2.trigger.PushTrigger;
import com.alvin.framework.message.push.v2.trigger.ScheduleTrigger;
import com.alvin.framework.message.push.v2.tunnel.AbstractStatefulTunnel;
import com.alvin.framework.message.push.v2.tunnel.AbstractTunnel;

import java.time.LocalDateTime;

/**
 * datetime 2020/1/16 15:47
 *
 * @author zhouwenxiang
 */
public class StandardTunnelPusher implements TunnelPusher {

    private AbstractTunnel tunnel;
    private AbstractMessageQueue queue;
    private Executor executor;

    @Override
    public void start() {
        queue.bindTunnel(tunnel);
        pushContinuously();
    }

    @Override
    public void add(Message message, boolean head) {

    }

    private void pushContinuously() {
        while (true) {
            Message message = queue.pop();
            if (message == null) {
                break;
            }

            PushPolicy policy = message.getPolicy();
            PushTunnelPolicy tunnelPolicy = policy.getTunnelPolicy();
            if (tunnelPolicy.isConnected()) {
                if (tunnelPolicy.isOrdered()) {
                    executor.executeInOrder(() -> tunnel.push(message.getData()));
                } else {
                    executor.execute(() -> tunnel.push(message.getData()));
                }
            }
            tunnel.push(message.getData());
        }
    }

    private void prePush(Message message) {
        PushPolicy policy = message.getPolicy();
        PushTunnelPolicy tunnelPolicy = policy.getTunnelPolicy();
        PushTrigger trigger = policy.getTrigger();
        if (trigger instanceof InstantlyTrigger) {

        } else if (trigger instanceof DelayTrigger) {

        } else if (trigger instanceof ScheduleTrigger) {

        }
    }

    private void pushAndHandle(Message message) {
        TunnelTip tunnelTip = push(message);
        if (tunnelTip.isOk()) {

        } else if (tunnelTip.getCode() == TunnelTipCodeEnum.NOT_CONNECTED) {

        } else if (tunnelTip.getCode() == TunnelTipCodeEnum.BLOCKED) {
            retry(message, tunnelTip.getSuggestTime());
        } else if (tunnelTip.getCode() == TunnelTipCodeEnum.ERROR) {
            retry(message, null);
        }
    }

    private TunnelTip push(Message message) {
        PushPolicy policy = message.getPolicy();
        PushTunnelPolicy tunnelPolicy = policy.getTunnelPolicy();
        if (tunnelPolicy.isConnected()) {
            return pushWhenConnected(message);
        } else {
            return tunnel.push(message.getData());
        }
    }

    private TunnelTip pushWhenConnected(Message message) {
        if (tunnel instanceof AbstractStatefulTunnel) {
            return ((AbstractStatefulTunnel) tunnel).pushWhenConnected(message.getData());
        }
        return tunnel.push(message.getData());
    }

    private void retry(Message message, LocalDateTime suggestedTime) {
        if (message.getPolicy().getRetryPolicy() != null && message.getPolicy().getRetryPolicy().getRetry() > 0) {
            message.getPolicy().setTunnelPolicy(message.getPolicy().getRetryPolicy().getTunnelPolicy());
            message.getPolicy().getRetryPolicy().setRetry(message.getPolicy().getRetryPolicy().getRetry() - 1);
            if (validSuggestTime(suggestedTime) && message.getPolicy().getRetryPolicy().isWait4block()) {
                message.getPolicy().setTrigger(ScheduleTrigger.at(suggestedTime));
            } else {
                message.getPolicy().setTrigger(message.getPolicy().getRetryPolicy().getTrigger());
            }
            executor.execute(() -> push(message));
        }
    }

    private boolean validSuggestTime(LocalDateTime time) {
        return time != null && time.isAfter(LocalDateTime.now());
    }
}
