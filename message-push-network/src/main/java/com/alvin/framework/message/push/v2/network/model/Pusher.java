package com.alvin.framework.message.push.v2.network.model;

import com.alvin.framework.message.push.v2.network.receiver.Receiver;
import com.alvin.framework.message.push.v2.network.tunnel.AbstractWrappedTunnel;
import lombok.Data;

/**
 * datetime 2020/1/18 21:53
 *
 * @author zhouwenxiang
 */
@Data
public class Pusher {

    protected Receiver receiver;
    protected AbstractWrappedTunnel tunnel;

    public Pusher(Receiver receiver, AbstractWrappedTunnel tunnel) {
        this.receiver = receiver;
        this.tunnel = tunnel;
    }

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        Pusher o = (Pusher) obj;
        return receiver.equals(o.getReceiver()) && tunnel.equals(o.getTunnel());
    }

    @Override
    public final int hashCode() {
        return this.receiver.hashCode() + this.tunnel.hashCode();
    }
}
