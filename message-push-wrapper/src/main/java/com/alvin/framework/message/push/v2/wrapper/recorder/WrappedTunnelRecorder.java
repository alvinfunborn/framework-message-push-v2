package com.alvin.framework.message.push.v2.wrapper.recorder;

import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;

import java.time.LocalDateTime;

/**
 * datetime 2020/1/18 20:41
 *
 * @author zhouwenxiang
 */
public interface WrappedTunnelRecorder {

    LocalDateTime lastSuccessTime(AbstractTunnel tunnel, long number);

    LocalDateTime lastAttemptTime(AbstractTunnel tunnel, long number);

    void recordError(Message message, TunnelTip tip);

    void recordSuccess(Message message);

    void recordAttempt(Message message);
}
