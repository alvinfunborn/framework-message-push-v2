package com.alvin.framework.message.push.v2.substance.rule;

import lombok.Getter;

/**
 * datetime 2020/1/22 9:39
 *
 * @author zhouwenxiang
 */
@Getter
public class TimeWindowRule extends AbstractRule implements RuleWithDuration, RuleWithQuota {

    protected long quota;
    protected long mills;

}
