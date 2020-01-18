package com.alvin.framework.message.push.v2.substance.tunnel;

import com.alvin.framework.message.push.v2.substance.model.TunnelTip;

import java.util.List;

/**
 * datetime 2020/1/16 10:58
 *
 * @author zhouwenxiang
 */
public class CombinedStatefulTunnel extends AbstractStatefulTunnel {

    @Override
    public boolean connected() {
        for (AbstractStatefulTunnel tunnel : tunnels) {
            if (tunnel.connected()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public TunnelTip doPush(String msg) {
        StringBuilder tip = new StringBuilder();
        for (AbstractStatefulTunnel tunnel : tunnels) {
            TunnelTip tunnelTip = tunnel.doPush(msg);
            if (tunnelTip.isOk()) {
                return tunnelTip;
            } else {
                tip.append(tunnelTip.getTip());
            }
        }
        return TunnelTip.error(tip.toString());
    }

    private List<AbstractStatefulTunnel> tunnels;

    public CombinedStatefulTunnel combine(AbstractStatefulTunnel abstractSingleTunnel) {
        this.tunnels.add(abstractSingleTunnel);
        return this;
    }

    public List<AbstractStatefulTunnel> getTunnels() {
        return tunnels;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        int size = tunnels.size();
        for (int i = 0; i < size; i++) {
            hashCode += tunnels.get(i).hashCode() + i;
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        CombinedStatefulTunnel o = (CombinedStatefulTunnel) obj;
        int thisSize = this.tunnels.size();
        int size = o.tunnels.size();
        if (thisSize != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (this.tunnels.get(i).hashCode() != o.tunnels.get(i).hashCode()) {
                return false;
            }
        }
        return true;
    }
}
