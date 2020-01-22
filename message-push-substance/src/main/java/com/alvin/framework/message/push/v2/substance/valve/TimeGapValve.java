package com.alvin.framework.message.push.v2.substance.valve;

import com.alvin.framework.message.push.v2.substance.business.Business;
import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.substance.rule.Rule;
import com.alvin.framework.message.push.v2.substance.rule.TimeGapRule;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * datetime 2020/1/22 10:09
 *
 * @author zhouwenxiang
 */
public class TimeGapValve extends AbstractValve {
    private static final long DELAY_SUGGEST_MILLS = 100;

    @Override
    public ValveTip control(Message msg) {
        TimeGapRule rule = (TimeGapRule) this.rule;
        if (rule.getMills() <= 0) {
            return ValveTip.ok();
        }
        if (rule.getPushScope().isSuccess()) {
            if (rule.getBizScope().isAllBiz()) {
                LocalDateTime time = tunnel.getRecorder().lastSuccessTime(1);
                if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() < rule.getMills()) {
                    return ValveTip.block(String.format("last success for all biz at %s, must wait for %d ms", time, rule.getMills()),
                            time.plus(rule.getMills() + DELAY_SUGGEST_MILLS, ChronoUnit.MILLIS));
                }
            }
            if (rule.getBizScope().isEachBiz()) {
                Business biz = msg.getBiz();
                LocalDateTime time = tunnel.getRecorder().lastSuccessTime(biz, 1);
                if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() < rule.getMills()) {
                    return ValveTip.block(String.format("last success for each biz at %s, must wait for %d ms", time, rule.getMills()),
                            time.plus(rule.getMills() + DELAY_SUGGEST_MILLS, ChronoUnit.MILLIS));
                }
            }
            if (rule.getBizScope().isSpecificBiz()) {
                Business biz = bizToControl(msg, rule.getBizScope());
                if (biz != null) {
                    LocalDateTime time = tunnel.getRecorder().lastSuccessTime(biz, 1);
                    if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() < rule.getMills()) {
                        return ValveTip.block(String.format("last success for this biz at %s, must wait for %d ms", time, rule.getMills()),
                                time.plus(rule.getMills() + DELAY_SUGGEST_MILLS, ChronoUnit.MILLIS));
                    }
                }
            }
        }
        if (rule.getPushScope().isAttempt()) {
            if (rule.getBizScope().isAllBiz()) {
                LocalDateTime time = tunnel.getRecorder().lastAttemptTime(1);
                if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() < rule.getMills()) {
                    return ValveTip.block(String.format("last attempt for all biz at %s, must wait for %d ms", time, rule.getMills()),
                            time.plus(rule.getMills() + DELAY_SUGGEST_MILLS, ChronoUnit.MILLIS));
                }
            }
            if (rule.getBizScope().isEachBiz()) {
                Business biz = msg.getBiz();
                LocalDateTime time = tunnel.getRecorder().lastAttemptTime(biz, 1);
                if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() < rule.getMills()) {
                    return ValveTip.block(String.format("last attempt for each biz at %s, must wait for %d ms", time, rule.getMills()),
                            time.plus(rule.getMills() + DELAY_SUGGEST_MILLS, ChronoUnit.MILLIS));
                }
            }
            if (rule.getBizScope().isSpecificBiz()) {
                Business biz = bizToControl(msg, rule.getBizScope());
                if (biz != null) {
                    LocalDateTime time = tunnel.getRecorder().lastAttemptTime(biz, 1);
                    if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() < rule.getMills()) {
                        return ValveTip.block(String.format("last attempt for this biz at %s, must wait for %d ms", time, rule.getMills()),
                                time.plus(rule.getMills() + DELAY_SUGGEST_MILLS, ChronoUnit.MILLIS));
                    }
                }
            }
        }
        if (rule.getPushScope().isError()) {
            if (rule.getBizScope().isAllBiz()) {
                LocalDateTime time = tunnel.getRecorder().lastErrorTime(1);
                if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() < rule.getMills()) {
                    return ValveTip.block(String.format("last error for all biz at %s, must wait for %d ms", time, rule.getMills()),
                            time.plus(rule.getMills() + DELAY_SUGGEST_MILLS, ChronoUnit.MILLIS));
                }
            }
            if (rule.getBizScope().isEachBiz()) {
                Business biz = msg.getBiz();
                LocalDateTime time = tunnel.getRecorder().lastErrorTime(biz, 1);
                if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() < rule.getMills()) {
                    return ValveTip.block(String.format("last error for each biz at %s, must wait for %d ms", time, rule.getMills()),
                            time.plus(rule.getMills() + DELAY_SUGGEST_MILLS, ChronoUnit.MILLIS));
                }
            }
            if (rule.getBizScope().isSpecificBiz()) {
                Business biz = bizToControl(msg, rule.getBizScope());
                if (biz != null) {
                    LocalDateTime time = tunnel.getRecorder().lastErrorTime(biz, 1);
                    if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() < rule.getMills()) {
                        return ValveTip.block(String.format("last error for this biz at %s, must wait for %d ms", time, rule.getMills()),
                                time.plus(rule.getMills() + DELAY_SUGGEST_MILLS, ChronoUnit.MILLIS));
                    }
                }
            }
        }
        return ValveTip.ok();
    }

    @Override
    public boolean support(Rule rule) {
        return rule instanceof TimeGapRule;
    }
}
