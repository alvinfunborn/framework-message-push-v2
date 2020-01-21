package com.alvin.framework.message.push.v2.substance.rule;

import lombok.Getter;

/**
 * datetime 2020/1/15 20:35
 *
 * @author zhouwenxiang
 */
@Getter
public class TimeWindowRuleOnSuccess extends Time implements RuleOnSuccess {

    protected long limit;
}
