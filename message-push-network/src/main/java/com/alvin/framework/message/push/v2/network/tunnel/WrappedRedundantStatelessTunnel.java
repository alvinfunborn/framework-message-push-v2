package com.alvin.framework.message.push.v2.network.tunnel;

import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.network.receiver.Receiver;

import java.util.List;

/**
 * datetime 2020/1/15 15:52
 *
 * @author zhouwenxiang
 */
public class WrappedRedundantStatelessTunnel extends AbstractWrappedStatelessTunnel {

    @Override
    public TunnelTip doPush(Receiver receiver, String msg) {
        StringBuilder tip = new StringBuilder();
        for (AbstractWrappedSingleTunnel tunnel : tunnels) {
            TunnelTip tunnelTip = tunnel.doPush(receiver, msg);
            if (tunnelTip.isOk()) {
                return tunnelTip;
            } else {
                tip.append(tunnelTip.getTip());
            }
        }
        return TunnelTip.error(tip.toString());
    }

    private List<AbstractWrappedSingleTunnel> tunnels;

    public WrappedRedundantStatelessTunnel combine(AbstractWrappedSingleTunnel abstractSingleTunnel) {
        this.tunnels.add(abstractSingleTunnel);
        return this;
    }

    public List<AbstractWrappedSingleTunnel> getTunnels() {
        return tunnels;
    }
}
