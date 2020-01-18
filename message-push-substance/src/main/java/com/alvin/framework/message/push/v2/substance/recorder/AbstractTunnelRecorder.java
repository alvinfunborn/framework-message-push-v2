package com.alvin.framework.message.push.v2.substance.recorder;

import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;

/**
 * datetime 2020/1/16 15:53
 *
 * @author zhouwenxiang
 */
public abstract class AbstractTunnelRecorder implements TunnelRecorder {

    protected AbstractTunnel tunnel;

    public void registerTunnel(AbstractTunnel tunnel) {
        this.tunnel = tunnel;
    }
}
