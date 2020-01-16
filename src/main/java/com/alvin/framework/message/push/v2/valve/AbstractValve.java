package com.alvin.framework.message.push.v2.valve;


import com.alvin.framework.message.push.v2.rule.Rule;
import com.alvin.framework.message.push.v2.tunnel.AbstractTunnel;

/**
 * datetime 2020/1/16 13:42
 *
 * @author zhouwenxiang
 */
public abstract class AbstractValve implements Valve {

    protected Rule rule;
    protected AbstractTunnel tunnel;

    public void setRule(Rule rule) {
        if (executable(rule)) {
            this.rule = rule;
        }
        throw new IllegalArgumentException("rule not supported in this valve");
    }

    public void registerTunnel(AbstractTunnel tunnel) {
        this.tunnel = tunnel;
    }

}
