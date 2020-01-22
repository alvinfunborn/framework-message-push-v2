package com.alvin.framework.message.push.v2.network.tunnel;

import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.network.model.WrappedMessage;
import com.alvin.framework.message.push.v2.network.pusher.AbstractWrappedPusher;
import com.alvin.framework.message.push.v2.network.receiver.Receiver;

/**
 * datetime 2020/1/18 22:38
 *
 * @author zhouwenxiang
 */
public abstract class AbstractWrappedTunnel implements WrappedTunnel {

    protected String name;
    protected AbstractWrappedPusher pusher;

    public abstract TunnelTip push(Receiver receiver, WrappedMessage msg);

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        AbstractWrappedTunnel o = (AbstractWrappedTunnel) obj;
        return this.name != null && this.name.equals(o.name);
    }

    @Override
    public final int hashCode() {
        return this.name.hashCode();
    }
}
