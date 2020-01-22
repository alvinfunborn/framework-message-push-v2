package com.alvin.framework.message.push.v2.wrapper.valve;

import com.alvin.framework.message.push.v2.substance.business.Business;
import com.alvin.framework.message.push.v2.substance.rule.RuleScopeOfBiz;
import com.alvin.framework.message.push.v2.wrapper.model.WrappedMessage;
import com.alvin.framework.message.push.v2.wrapper.pusher.AbstractWrappedPusher;
import com.alvin.framework.message.push.v2.wrapper.receiver.Receiver;
import com.alvin.framework.message.push.v2.wrapper.rule.AbstractWrappedRule;
import com.alvin.framework.message.push.v2.wrapper.rule.RuleScopeOfReceiver;
import com.alvin.framework.message.push.v2.wrapper.rule.RuleScopeOfTunnel;
import com.alvin.framework.message.push.v2.wrapper.tunnel.AbstractWrappedTunnel;

/**
 * datetime 2020/1/21 13:44
 *
 * @author zhouwenxiang
 */
public abstract class AbstractWrappedValve implements WrappedValve {

    protected AbstractWrappedRule rule;
    protected AbstractWrappedPusher pusher;

    public void setRule(AbstractWrappedRule rule) {
        if (support(rule)) {
            this.rule = rule;
        }
        throw new IllegalArgumentException("rule not supported in this valve");
    }

    public void bindPusher(AbstractWrappedPusher pusher) {
        this.pusher = pusher;
    }


    protected Business bizToControl(WrappedMessage msg, RuleScopeOfBiz ruleScopeOfBiz) {
        for (Business specificBiz : ruleScopeOfBiz.getSpecificBizs()) {
            if (pusher.getBusinessFactory().hasHierarchy(specificBiz, msg.getBiz())) {
                return specificBiz;
            }
        }
        return null;
    }

    protected Receiver receiverToControl(Receiver receiver, RuleScopeOfReceiver ruleScopeOfReceiver) {
        for (Receiver specificReceiver : ruleScopeOfReceiver.getSpecificReceivers()) {
            if (pusher.getReceiverFactory().hasHierarchy(specificReceiver, receiver)) {
                return specificReceiver;
            }
        }
        return null;
    }

    protected AbstractWrappedTunnel tunnelToControl(AbstractWrappedTunnel tunnel, RuleScopeOfTunnel ruleScopeOfTunnel) {
        for (AbstractWrappedTunnel specificTunnel : ruleScopeOfTunnel.getSpecificTunnels()) {
            if (pusher.getTunnelFactory().hasHierarchy(specificTunnel, tunnel)) {
                return specificTunnel;
            }
        }
        return null;
    }

}
