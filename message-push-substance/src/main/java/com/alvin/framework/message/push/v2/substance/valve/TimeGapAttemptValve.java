package com.alvin.framework.message.push.v2.substance.valve;

import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.substance.rule.Rule;
import com.alvin.framework.message.push.v2.substance.rule.TimeWindowRuleOnAttempt;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * datetime 2020/1/21 17:51
 *
 * @author zhouwenxiang
 */
public class TimeGapAttemptValve extends AbstractValve {

    @Override
    public ValveTip control() {
        assert rule != null;
        TimeWindowRuleOnAttempt rule = (TimeWindowRuleOnAttempt) this.rule;
        if (rule.getMills() > 0) {
            LocalDateTime time = tunnel.getRecorder().lastAttemptTime(1);
            if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() < rule.getMills()) {
                return ValveTip.block(String.format("last attempt at %s, must wait for %d ms", time, rule.getMills()),
                        time.plus(rule.getMills() + 3000, ChronoUnit.MILLIS));
            }
        }
        return ValveTip.ok();
    }

    @Override
    public boolean support(Rule rule) {
        return rule instanceof TimeWindowRuleOnAttempt;
    }
}
