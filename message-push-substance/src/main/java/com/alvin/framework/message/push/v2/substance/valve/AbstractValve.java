package com.alvin.framework.message.push.v2.substance.valve;

import com.alvin.framework.message.push.v2.substance.business.Business;
import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.rule.AbstractRule;
import com.alvin.framework.message.push.v2.substance.rule.RuleScopeOfBiz;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;

import java.util.List;

/**
 * datetime 2020/1/16 13:42
 *
 * @author zhouwenxiang
 */
public abstract class AbstractValve implements Valve {

    protected AbstractRule rule;
    protected AbstractTunnel tunnel;

    public void setRule(AbstractRule rule) {
        if (support(rule)) {
            this.rule = rule;
        }
        throw new IllegalArgumentException("rule not supported in this valve");
    }

    public void bindTunnel(AbstractTunnel tunnel) {
        this.tunnel = tunnel;
    }

    protected Business bizToControl(Message msg, RuleScopeOfBiz ruleScopeOfBiz) {
        for (Business specificBiz : ruleScopeOfBiz.getSpecificBizs()) {
            if (tunnel.getBusinessFactory().hasHierarchy(specificBiz, msg.getBiz())) {
                return specificBiz;
            }
        }
        return null;
    }
}
