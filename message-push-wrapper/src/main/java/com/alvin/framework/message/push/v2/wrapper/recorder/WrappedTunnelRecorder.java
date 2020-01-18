package com.alvin.framework.message.push.v2.wrapper.recorder;

import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;

import java.time.LocalDateTime;

/**
 * datetime 2020/1/18 20:41
 *
 * @author zhouwenxiang
 */
public interface WrappedTunnelRecorder {

    LocalDateTime lastSuccessTime(AbstractTunnel tunnel);

    LocalDateTime lastSuccessTime(AbstractTunnel tunnel, long rindex);

    LocalDateTime lastAttemptTime(AbstractTunnel tunnel);

    LocalDateTime lastAttemptTime(AbstractTunnel tunnel, long rindex);

    void recordSuccess(AbstractTunnel tunnel);

    void recordAttempt(AbstractTunnel tunnel);
}
