package com.alvin.framework.message.push.v2.substance.valve;


import com.alvin.framework.message.push.v2.substance.rule.Rule;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;

/**
 * datetime 2020/1/16 13:42
 *
 * @author zhouwenxiang
 */
public abstract class AbstractValve implements Valve {

    protected Rule rule;
    protected AbstractTunnel tunnel;

    public void setRule(Rule rule) {
        if (support(rule)) {
            this.rule = rule;
        }
        throw new IllegalArgumentException("rule not supported in this valve");
    }

    public void bindTunnel(AbstractTunnel tunnel) {
        this.tunnel = tunnel;
    }

}
