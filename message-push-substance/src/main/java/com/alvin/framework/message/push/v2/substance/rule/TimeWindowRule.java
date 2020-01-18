package com.alvin.framework.message.push.v2.substance.rule;

import lombok.Builder;
import lombok.Getter;

/**
 * datetime 2020/1/15 20:35
 *
 * @author zhouwenxiang
 */
@Getter
@Builder
public class TimeWindowRule implements RuleOnAttempt, RuleOnSuccess {

    private long windowMills;
    private long attemptLimit;
    private long successLimit;
}
