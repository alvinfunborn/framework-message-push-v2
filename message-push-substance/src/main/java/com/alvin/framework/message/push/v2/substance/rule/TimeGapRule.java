package com.alvin.framework.message.push.v2.substance.rule;

import lombok.Data;

/**
 * datetime 2020/1/15 20:40
 *
 * @author zhouwenxiang
 */
@Data
public class TimeGapRule implements RuleOnAttempt, RuleOnSuccess {

    private long attemptGapMills;
    private long successGapMills;
}