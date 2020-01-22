package com.alvin.framework.message.push.v2.network.valve;

import com.alvin.framework.message.push.v2.substance.business.Business;
import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.network.model.WrappedMessage;
import com.alvin.framework.message.push.v2.network.receiver.Receiver;
import com.alvin.framework.message.push.v2.network.rule.AbstractWrappedRule;
import com.alvin.framework.message.push.v2.network.rule.WrappedTimeWindowRule;
import com.alvin.framework.message.push.v2.network.tunnel.AbstractWrappedTunnel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * datetime 2020/1/22 11:29
 *
 * @author zhouwenxiang
 */
public class WrappedTimeWindowValve extends AbstractWrappedValve {
    @Override
    public ValveTip control(Receiver receiver, WrappedMessage message, AbstractWrappedTunnel tunnel) {
        WrappedTimeWindowRule rule = (WrappedTimeWindowRule) this.rule;
        if (rule.getQuota() <= 0 || rule.getMills() <= 0) {
            return ValveTip.ok();
        }
        if (rule.getPushScope().isSuccess()) {
            if (rule.getBizScope().isAllBiz()) {
                if (rule.getReceiverScope().isAllReceiver()) {
                    if (rule.getTunnelScope().isAllTunnel()) {
                        LocalDateTime time = pusher.getRecorder().lastSuccessTime(rule.getQuota(), null, null, null);
                        if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                            LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                            return ValveTip.block(String.format("%d push from %s to %s", rule.getQuota(), time, suggestTime),
                                    suggestTime);
                        }
                    }
                    if (rule.getTunnelScope().isEachTunnel()) {
                        LocalDateTime time = pusher.getRecorder().lastSuccessTime(rule.getQuota(), null, null, tunnel);
                        if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                            LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                            return ValveTip.block(String.format("%d push from %s to %s", rule.getQuota(), time, suggestTime),
                                    suggestTime);
                        }
                    }
                }
                LocalDateTime time = pusher.getRecorder().lastSuccessTime(rule.getQuota());
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
        return null;
    }

    @Override
    public boolean support(AbstractWrappedRule rule) {
        return rule instanceof WrappedTimeWindowRule;
    }

    private ValveTip control(WrappedTimeWindowRule rule, Receiver receiver, Business biz, AbstractWrappedTunnel tunnel) {
        if (rule.getPushScope().isSuccess()) {
            LocalDateTime time = pusher.getRecorder().lastSuccessTime(rule.getQuota(), receiver, biz, tunnel);
            if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                return ValveTip.block(String.format("%d success push from %s to %s, rule:%s", rule.getQuota(), time, suggestTime, rule),
                        suggestTime);
            }
        }
        if (rule.getPushScope().isAttempt()) {
            LocalDateTime time = pusher.getRecorder().lastAttemptTime(rule.getQuota(), receiver, biz, tunnel);
            if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                return ValveTip.block(String.format("%d attempt push from %s to %s, rule:%s", rule.getQuota(), time, suggestTime, rule),
                        suggestTime);
            }
        }
        if (rule.getPushScope().isError()) {
            LocalDateTime time = pusher.getRecorder().lastErrorTime(rule.getQuota(), receiver, biz, tunnel);
            if (time != null && Duration.between(time, LocalDateTime.now()).toMillis() <= rule.getMills()) {
                LocalDateTime suggestTime = time.plus(rule.getMills(), ChronoUnit.MILLIS);
                return ValveTip.block(String.format("%d error push from %s to %s, rule:%s", rule.getQuota(), time, suggestTime, rule),
                        suggestTime);
            }
        }
    }
}
