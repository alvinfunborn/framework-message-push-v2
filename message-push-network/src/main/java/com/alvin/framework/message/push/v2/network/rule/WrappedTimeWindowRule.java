package com.alvin.framework.message.push.v2.network.rule;

import com.alvin.framework.message.push.v2.substance.rule.RuleWithDuration;
import com.alvin.framework.message.push.v2.substance.rule.RuleWithQuota;
import lombok.Getter;
import lombok.ToString;

/**
 * datetime 2020/1/22 11:08
 *
 * @author zhouwenxiang
 */
@ToString
@Getter
public class WrappedTimeWindowRule extends AbstractWrappedRule implements RuleWithQuota, RuleWithDuration {

    protected long mills;
    protected long quota;
}
