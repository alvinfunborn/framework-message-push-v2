package com.alvin.framework.message.push.v2.tunnel;

import com.alvin.framework.message.push.v2.model.TunnelResult;
import com.alvin.framework.message.push.v2.model.enums.TunnelResultEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * datetime 2020/1/15 15:52
 *
 * @author zhouwenxiang
 */
public class CombinedTunnel implements Tunnel {

    @Override
    public TunnelResult push(String receiver, String msg) {
        for (Tunnel tunnel : tunnels) {
            TunnelResult tunnelResult = tunnel.push(receiver, msg);
            if (tunnelResult.isSuccessful()) {
                return tunnelResult;
            }
        }
        return TunnelResult.ofFailed(TunnelResultEnum.NO_TUNNEL_SUCCEED);
    }

    private List<SingleTunnel> tunnels;

    public CombinedTunnel combine(SingleTunnel singleTunnel) {
        this.tunnels.add(singleTunnel);
        return this;
    }

    public List<SingleTunnel> getTunnels() {
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
