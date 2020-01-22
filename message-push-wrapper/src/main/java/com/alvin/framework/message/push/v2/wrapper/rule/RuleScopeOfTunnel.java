package com.alvin.framework.message.push.v2.wrapper.rule;

import com.alvin.framework.message.push.v2.substance.rule.RuleScope;
import com.alvin.framework.message.push.v2.wrapper.tunnel.AbstractWrappedTunnel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * datetime 2020/1/22 11:26
 *
 * @author zhouwenxiang
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class RuleScopeOfTunnel implements RuleScope {

    public static final RuleScopeOfTunnel DEFAULT = eachTunnel();

    protected boolean allTunnel;
    protected boolean eachTunnel;
    protected boolean specificTunnel;
    protected List<AbstractWrappedTunnel> specificTunnels;

    public static RuleScopeOfTunnel eachTunnel() {
        return new RuleScopeOfTunnel(false, true, false, null);
    }
}
