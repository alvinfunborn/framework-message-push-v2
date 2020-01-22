package com.alvin.framework.message.push.v2.network.rule;

import com.alvin.framework.message.push.v2.substance.rule.AbstractRule;
import lombok.Getter;

/**
 * datetime 2020/1/21 14:08
 *
 * @author zhouwenxiang
 */
@Getter
public abstract class AbstractWrappedRule extends AbstractRule {

    protected RuleScopeOfReceiver receiverScope;
    protected RuleScopeOfTunnel tunnelScope;
}
