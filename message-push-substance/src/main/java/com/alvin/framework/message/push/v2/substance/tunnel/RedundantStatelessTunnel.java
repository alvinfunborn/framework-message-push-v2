package com.alvin.framework.message.push.v2.substance.tunnel;

import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.recorder.AbstractTunnelRecorder;
import com.alvin.framework.message.push.v2.substance.valve.AbstractValve;

import java.util.List;

/**
 * datetime 2020/1/15 15:52
 *
 * @author zhouwenxiang
 */
public class RedundantStatelessTunnel extends AbstractStatelessTunnel {

    public RedundantStatelessTunnel(String name, AbstractTunnelRecorder recorder, List<AbstractValve> valves) {
        super(name, recorder, valves);
    }

    @Override
    public TunnelTip doPush(String msg) {
        StringBuilder tip = new StringBuilder();
        for (AbstractSingleTunnel tunnel : tunnels) {
            TunnelTip tunnelTip = tunnel.doPush(msg);
            if (tunnelTip.isOk()) {
                return tunnelTip;
            } else {
                tip.append(tunnelTip.getTip());
            }
        }
        return TunnelTip.error(tip.toString());
    }

    private List<AbstractSingleTunnel> tunnels;

    public RedundantStatelessTunnel combine(AbstractSingleTunnel abstractSingleTunnel) {
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
        RedundantStatelessTunnel o = (RedundantStatelessTunnel) obj;
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
