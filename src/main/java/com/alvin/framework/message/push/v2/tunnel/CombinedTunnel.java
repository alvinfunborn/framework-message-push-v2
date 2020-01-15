package com.alvin.framework.message.push.v2.tunnel;

import com.alvin.framework.message.push.v2.model.TunnelTip;
import com.alvin.framework.message.push.v2.model.enums.TunnelTipEnum;

import java.util.List;

/**
 * datetime 2020/1/15 15:52
 *
 * @author zhouwenxiang
 */
public class CombinedTunnel extends AbstractTunnel {

    @Override
    public TunnelTip push(String receiver, String msg) {
        for (AbstractSingleTunnel tunnel : tunnels) {
            TunnelTip tunnelTip = tunnel.push(receiver, msg);
            if (tunnelTip.isSuccessful()) {
                return tunnelTip;
            }
        }
        return TunnelTip.error(TunnelTipEnum.NO_TUNNEL_SUCCEED);
    }

    private List<AbstractSingleTunnel> tunnels;

    public CombinedTunnel combine(AbstractSingleTunnel abstractSingleTunnel) {
        this.tunnels.add(abstractSingleTunnel);
        return this;
    }

    public List<AbstractSingleTunnel> getTunnels() {
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
        CombinedTunnel o = (CombinedTunnel) obj;
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
