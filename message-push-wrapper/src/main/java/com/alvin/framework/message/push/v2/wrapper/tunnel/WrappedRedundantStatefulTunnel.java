package com.alvin.framework.message.push.v2.wrapper.tunnel;

import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.wrapper.receiver.Receiver;

import java.util.List;

/**
 * datetime 2020/1/16 10:58
 *
 * @author zhouwenxiang
 */
public class WrappedRedundantStatefulTunnel extends AbstractWrappedStatefulTunnel {

    @Override
    public boolean connected(Receiver receiver) {
        for (AbstractWrappedStatefulTunnel tunnel : tunnels) {
            if (tunnel.connected(receiver)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public TunnelTip doPush(Receiver receiver, String msg) {
        StringBuilder tip = new StringBuilder();
        for (AbstractWrappedStatefulTunnel tunnel : tunnels) {
            TunnelTip tunnelTip = tunnel.doPush(receiver, msg);
            if (tunnelTip.isOk()) {
                return tunnelTip;
            } else {
                tip.append(tunnelTip.getTip());
            }
        }
        return TunnelTip.error(tip.toString());
    }

    private List<AbstractWrappedStatefulTunnel> tunnels;

    public WrappedRedundantStatefulTunnel combine(AbstractWrappedStatefulTunnel abstractSingleTunnel) {
        this.tunnels.add(abstractSingleTunnel);
        return this;
    }

    public List<AbstractWrappedStatefulTunnel> getTunnels() {
        return tunnels;
    }
}
