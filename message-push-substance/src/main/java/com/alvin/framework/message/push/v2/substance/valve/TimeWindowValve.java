package com.alvin.framework.message.push.v2.substance.valve;

import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.substance.rule.Rule;
import com.alvin.framework.message.push.v2.substance.rule.TimeWindowRule;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * datetime 2020/1/16 13:43
 *
 * @author zhouwenxiang
 */
public class TimeWindowValve extends AbstractValve {

    @Override
    public ValveTip control() {
        assert rule != null;
        TimeWindowRule timeWindowRule = (TimeWindowRule) rule;
        if (timeWindowRule.getSuccessLimit() > 0) {
            LocalDateTime time = tunnel.getRecorder().lastSuccessTime(timeWindowRule.getSuccessLimit());
            if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= timeWindowRule.getWindowMills()) {
                LocalDateTime suggestTime = time.plus(timeWindowRule.getWindowMills(), ChronoUnit.MILLIS);
                return ValveTip.block(String.format("%d successful push allowed from %s to %s", timeWindowRule.getSuccessLimit(), time, suggestTime),
                        suggestTime);
            }
        }
        if (timeWindowRule.getAttemptLimit() > 0) {
            LocalDateTime time = tunnel.getRecorder().lastAttemptTime(timeWindowRule.getAttemptLimit());
            if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= timeWindowRule.getWindowMills()) {
                LocalDateTime suggestTime = time.plus(timeWindowRule.getWindowMills(), ChronoUnit.MILLIS);
                return ValveTip.block(String.format("%d attempt allowed from %s to %s", timeWindowRule.getAttemptLimit(), time, suggestTime),
                        suggestTime);
            }
        }
        return ValveTip.ok();
    }

    @Override
    public boolean executable(Rule rule) {
        return rule instanceof TimeWindowRule;
    }
}
