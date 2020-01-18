package com.alvin.framework.message.push.v2.substance.queue;

import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;
import lombok.Getter;

/**
 * datetime 2020/1/16 15:50
 *
 * @author zhouwenxiang
 */
@Getter
public abstract class AbstractMessageQueue implements MessageQueue {

    protected String name;
    protected AbstractTunnel tunnel;

    public void bindTunnel(AbstractTunnel tunnel) {
        this.tunnel = tunnel;
    }
}
