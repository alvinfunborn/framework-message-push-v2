package com.alvin.framework.message.push.v2.queue;

import com.alvin.framework.message.push.v2.tunnel.AbstractTunnel;

/**
 * datetime 2020/1/16 15:50
 *
 * @author zhouwenxiang
 */
public abstract class AbstractMessageQueue implements MessageQueue {

    protected AbstractTunnel tunnel;

    public void bindTunnel(AbstractTunnel tunnel) {
        this.tunnel = tunnel;
    }
}
