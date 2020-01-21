package com.alvin.framework.message.push.v2.substance.valve;

import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.substance.rule.Rule;
import com.alvin.framework.message.push.v2.substance.rule.TimeWindowRuleOnSuccess;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * datetime 2020/1/16 13:43
 *
 * @author zhouwenxiang
 */
public class TimeWindowSuccessValve extends AbstractValve {

    @Override
    public ValveTip control() {
        assert rule != null;
        TimeWindowRuleOnSuccess timeWindowRuleOnSuccess = (TimeWindowRuleOnSuccess) rule;
        if (timeWindowRuleOnSuccess.getLimit() > 0) {
            LocalDateTime time = tunnel.getRecorder().lastSuccessTime(timeWindowRuleOnSuccess.getLimit());
            if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= timeWindowRuleOnSuccess.getMills()) {
                LocalDateTime suggestTime = time.plus(timeWindowRuleOnSuccess.getMills(), ChronoUnit.MILLIS);
                return ValveTip.block(String.format("%d successful push allowed from %s to %s", timeWindowRuleOnSuccess.getLimit(), time, suggestTime),
                        suggestTime);
            }
        }
        return ValveTip.ok();
    }

    @Override
    public boolean support(Rule rule) {
        return rule instanceof TimeWindowRuleOnSuccess;
    }
}
