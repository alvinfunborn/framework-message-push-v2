package com.alvin.framework.message.push.v2.substance.valve;

import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.substance.rule.Rule;
import com.alvin.framework.message.push.v2.substance.rule.TimeRuleOnSuccess;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * datetime 2020/1/16 15:02
 *
 * @author zhouwenxiang
 */
public class TimeGapSuccessValve extends AbstractValve {

    @Override
    public ValveTip control() {
        assert rule != null;
        TimeRuleOnSuccess rule = (TimeRuleOnSuccess) this.rule;
        if (rule.getMills() > 0) {
            LocalDateTime time = tunnel.getRecorder().lastSuccessTime(1);
            if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() < rule.getMills()) {
                return ValveTip.block(String.format("last successful push at %s, must wait for %d ms", time, rule.getMills()),
                        time.plus(rule.getMills() + 3000, ChronoUnit.MILLIS));
            }
        }
        return ValveTip.ok();
    }

    @Override
    public boolean support(Rule rule) {
        return rule instanceof TimeRuleOnSuccess;
    }
}
