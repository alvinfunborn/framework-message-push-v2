package com.alvin.framework.message.push.v2.substance.valve;

import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.substance.rule.Rule;
import com.alvin.framework.message.push.v2.substance.rule.TimeWindowRuleOnAttempt;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * datetime 2020/1/21 17:49
 *
 * @author zhouwenxiang
 */
public class TimeWindowAttemptValve extends AbstractValve {

    @Override
    public ValveTip control() {
        assert rule != null;
        TimeWindowRuleOnAttempt rule = (TimeWindowRuleOnAttempt) this.rule;
        if (rule.getLimit() > 0) {
            LocalDateTime time = tunnel.getRecorder().lastAttemptTime(rule.getLimit());
            if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                return ValveTip.block(String.format("%d attempt allowed from %s to %s", rule.getLimit(), time, suggestTime),
                        suggestTime);
            }
        }
        return ValveTip.ok();
    }

    @Override
    public boolean support(Rule rule) {
        return rule instanceof TimeWindowRuleOnAttempt;
    }
}
