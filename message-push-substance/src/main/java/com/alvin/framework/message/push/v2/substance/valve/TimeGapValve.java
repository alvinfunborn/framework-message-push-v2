package com.alvin.framework.message.push.v2.substance.valve;

import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.substance.rule.Rule;
import com.alvin.framework.message.push.v2.substance.rule.TimeGapRule;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * datetime 2020/1/16 15:02
 *
 * @author zhouwenxiang
 */
public class TimeGapValve extends AbstractValve {

    @Override
    public ValveTip control() {
        assert rule != null;
        TimeGapRule timeGapRule = (TimeGapRule) rule;
        if (timeGapRule.getSuccessGapMills() > 0) {
            LocalDateTime time = tunnel.getRecorder().lastSuccessTime();
            if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() < timeGapRule.getSuccessGapMills()) {
                return ValveTip.block(String.format("last successful push at %s, must wait for %d ms", time, timeGapRule.getSuccessGapMills()),
                        time.plus(timeGapRule.getSuccessGapMills() + 3000, ChronoUnit.MILLIS));
            }
        }
        if (timeGapRule.getAttemptGapMills() > 0) {
            LocalDateTime time = tunnel.getRecorder().lastAttemptTime();
            if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() < timeGapRule.getAttemptGapMills()) {
                return ValveTip.block(String.format("last attempt at %s, must wait for %d ms", time, timeGapRule.getAttemptGapMills()),
                        time.plus(timeGapRule.getAttemptGapMills() + 3000, ChronoUnit.MILLIS));
            }
        }
        return ValveTip.ok();
    }

    @Override
    public boolean executable(Rule rule) {
        return rule instanceof TimeGapRule;
    }
}
