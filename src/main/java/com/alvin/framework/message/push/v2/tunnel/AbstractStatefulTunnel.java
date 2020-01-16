package com.alvin.framework.message.push.v2.tunnel;

import com.alvin.framework.message.push.v2.model.TunnelTip;
import com.alvin.framework.message.push.v2.model.ValveTip;
import com.alvin.framework.message.push.v2.model.enums.TunnelTipEnum;
import com.alvin.framework.message.push.v2.valve.Valve;

/**
 * datetime 2020/1/15 15:51
 *
 * @author zhouwenxiang
 */
public abstract class AbstractStatefulTunnel extends AbstractSingleTunnel {

    /**
     * if receiver connected to this tunnel
     *
     * @return true if connected
     */
    public abstract boolean connected();

    @Override
    public TunnelTip push(String msg) {
        for (Valve valve : valves) {
            ValveTip valveTip = valve.control();
            if (!valveTip.isOk()) {
                return TunnelTip.blocked(valveTip);
            }
        }
        return doPush(msg);
    }

    /**
     * check connection before pushing
     * @param msg data
     * @return TunnelResult
     */
    public TunnelTip pushWhenConnected(String msg) {
        for (Valve valve : valves) {
            ValveTip valveTip = valve.control();
            if (!valveTip.isOk()) {
                return TunnelTip.blocked(valveTip);
            }
        }
        if (connected()) {
            return doPush(msg);
        }
        return TunnelTip.notConnected();
    }
}
