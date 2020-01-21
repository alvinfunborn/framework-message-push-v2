package com.alvin.framework.message.push.v2.wrapper.valve;

import com.alvin.framework.message.push.v2.wrapper.rule.WrappedRule;
import com.alvin.framework.message.push.v2.wrapper.tunnel.AbstractWrappedTunnel;

/**
 * datetime 2020/1/21 13:44
 *
 * @author zhouwenxiang
 */
public abstract class AbstractWrappedValve implements WrappedValve {

    protected WrappedRule rule;
    protected AbstractWrappedTunnel tunnel;

    public void setRule(WrappedRule rule) {
        if (support(rule)) {
            this.rule = rule;
        }
        throw new IllegalArgumentException("rule not supported in this valve");
    }

    public void bindTunnel(AbstractWrappedTunnel tunnel) {
        this.tunnel = tunnel;
    }

}
