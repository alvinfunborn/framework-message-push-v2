package com.alvin.framework.message.push.v2.substance.rule;

import lombok.Getter;

/**
 * datetime 2020/1/21 17:48
 *
 * @author zhouwenxiang
 */
@Getter
public class TimeWindowRuleOnAttempt extends Time implements RuleOnAttempt {

    protected long limit;
}
