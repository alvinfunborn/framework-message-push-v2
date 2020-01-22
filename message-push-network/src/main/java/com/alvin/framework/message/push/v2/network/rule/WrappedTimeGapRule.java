package com.alvin.framework.message.push.v2.network.rule;

import com.alvin.framework.message.push.v2.substance.rule.RuleWithDuration;
import lombok.Getter;

/**
 * datetime 2020/1/22 14:27
 *
 * @author zhouwenxiang
 */
@Getter
public class WrappedTimeGapRule extends AbstractWrappedRule implements RuleWithDuration {

    protected long mills;
}
