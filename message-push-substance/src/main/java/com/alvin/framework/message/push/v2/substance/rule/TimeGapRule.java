package com.alvin.framework.message.push.v2.substance.rule;

import lombok.Getter;

/**
 * datetime 2020/1/22 10:08
 *
 * @author zhouwenxiang
 */
@Getter
public class TimeGapRule extends AbstractRule implements RuleWithDuration {

    protected long mills;
}
