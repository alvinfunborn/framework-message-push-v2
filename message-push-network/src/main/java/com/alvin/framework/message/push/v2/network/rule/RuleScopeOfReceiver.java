package com.alvin.framework.message.push.v2.network.rule;

import com.alvin.framework.message.push.v2.substance.rule.RuleScope;
import com.alvin.framework.message.push.v2.network.receiver.Receiver;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * datetime 2020/1/22 10:24
 *
 * @author zhouwenxiang
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class RuleScopeOfReceiver implements RuleScope {

    public static final RuleScopeOfReceiver DEFAULT = eachReceiver();

    protected boolean allReceiver;
    protected boolean eachReceiver;
    protected boolean specificReceiver;
    protected List<Receiver> specificReceivers;

    public static RuleScopeOfReceiver eachReceiver() {
        return new RuleScopeOfReceiver(false, true, false, null);
    }
}
