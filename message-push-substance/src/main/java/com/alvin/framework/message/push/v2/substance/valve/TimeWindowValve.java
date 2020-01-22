package com.alvin.framework.message.push.v2.substance.valve;

import com.alvin.framework.message.push.v2.substance.business.Business;
import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.substance.rule.Rule;
import com.alvin.framework.message.push.v2.substance.rule.TimeWindowRule;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * datetime 2020/1/22 9:58
 *
 * @author zhouwenxiang
 */
public class TimeWindowValve extends AbstractValve {

    @Override
    public ValveTip control(Message msg) {
        assert rule != null;
        TimeWindowRule rule = (TimeWindowRule) this.rule;
        if (rule.getQuota() <= 0 || rule.getMills() <= 0) {
            return ValveTip.ok();
        }
        if (rule.getPushScope().isSuccess()) {
            if (rule.getBizScope().isAllBiz()) {
                LocalDateTime time = tunnel.getRecorder().lastSuccessTime(rule.getQuota());
                if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                    LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                    return ValveTip.block(String.format("%d successful push for all biz allowed from %s to %s", rule.getQuota(), time, suggestTime),
                            suggestTime);
                }
            }
            if (rule.getBizScope().isEachBiz()) {
                Business biz = msg.getBiz();
                LocalDateTime time = tunnel.getRecorder().lastSuccessTime(biz, rule.getQuota());
                if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                    LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                    return ValveTip.block(String.format("%d successful push for each biz allowed from %s to %s", rule.getQuota(), time, suggestTime),
                            suggestTime);
                }
            }
            if (rule.getBizScope().isSpecificBiz()) {
                Business biz = bizToControl(msg, rule.getBizScope());
                if (biz != null) {
                    LocalDateTime time = tunnel.getRecorder().lastSuccessTime(biz, rule.getQuota());
                    if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                        LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                        return ValveTip.block(String.format("%d attempt push for this biz allowed from %s to %s", rule.getQuota(), time, suggestTime),
                                suggestTime);
                    }
                }
            }
        }
        if (rule.getPushScope().isAttempt()) {
            if (rule.getBizScope().isAllBiz()) {
                LocalDateTime time = tunnel.getRecorder().lastAttemptTime(rule.getQuota());
                if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                    LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                    return ValveTip.block(String.format("%d attempt push for all biz allowed from %s to %s", rule.getQuota(), time, suggestTime),
                            suggestTime);
                }
            }
            if (rule.getBizScope().isEachBiz()) {
                Business biz = msg.getBiz();
                LocalDateTime time = tunnel.getRecorder().lastAttemptTime(biz, rule.getQuota());
                if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                    LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                    return ValveTip.block(String.format("%d attempt push for each biz allowed from %s to %s", rule.getQuota(), time, suggestTime),
                            suggestTime);
                }
            }
            if (rule.getBizScope().isSpecificBiz()) {
                Business biz = bizToControl(msg, rule.getBizScope());
                if (biz != null) {
                    LocalDateTime time = tunnel.getRecorder().lastAttemptTime(biz, rule.getQuota());
                    if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                        LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                        return ValveTip.block(String.format("%d attempt push for this biz allowed from %s to %s", rule.getQuota(), time, suggestTime),
                                suggestTime);
                    }
                }
            }
        }
        if (rule.getPushScope().isError()) {
            if (rule.getBizScope().isAllBiz()) {
                LocalDateTime time = tunnel.getRecorder().lastErrorTime(rule.getQuota());
                if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                    LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                    return ValveTip.block(String.format("%d error push for all biz allowed from %s to %s", rule.getQuota(), time, suggestTime),
                            suggestTime);
                }
            }
            if (rule.getBizScope().isEachBiz()) {
                Business biz = msg.getBiz();
                LocalDateTime time = tunnel.getRecorder().lastErrorTime(biz, rule.getQuota());
                if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                    LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                    return ValveTip.block(String.format("%d error push for each biz allowed from %s to %s", rule.getQuota(), time, suggestTime),
                            suggestTime);
                }
            }
            if (rule.getBizScope().isSpecificBiz()) {
                Business biz = bizToControl(msg, rule.getBizScope());
                if (biz != null) {
                    LocalDateTime time = tunnel.getRecorder().lastErrorTime(biz, rule.getQuota());
                    if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                        LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                        return ValveTip.block(String.format("%d error push for this biz allowed from %s to %s", rule.getQuota(), time, suggestTime),
                                suggestTime);
                    }
                }
            }
        }
        return ValveTip.ok();
    }

    @Override
    public boolean support(Rule rule) {
        return rule instanceof TimeWindowRule;
    }
}
