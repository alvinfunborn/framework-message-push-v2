package com.alvin.framework.message.push.v2.network.recorder;

import com.alvin.framework.message.push.v2.substance.business.Business;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.network.model.WrappedMessage;
import com.alvin.framework.message.push.v2.network.receiver.Receiver;
import com.alvin.framework.message.push.v2.network.tunnel.AbstractWrappedTunnel;

import java.time.LocalDateTime;

/**
 * datetime 2020/1/18 20:41
 *
 * @author zhouwenxiang
 */
public interface WrappedPushRecorder {

    LocalDateTime lastSuccessTime(long number, Receiver receiver, Business bizs, AbstractWrappedTunnel tunnels);
    LocalDateTime lastAttemptTime(long number, Receiver receiver, Business biz, AbstractWrappedTunnel tunnels);
    LocalDateTime lastErrorTime(long number, Receiver receivers, Business biz, AbstractWrappedTunnel tunnels);

    void recordError(WrappedMessage message, TunnelTip tip);

    void recordSuccess(WrappedMessage message);

    void recordAttempt(WrappedMessage message);
}
