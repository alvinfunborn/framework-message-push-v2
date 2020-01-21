package com.alvin.framework.message.push.v2.wrapper.tunnel;

import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.substance.valve.Valve;

/**
 * datetime 2020/1/21 11:40
 *
 * @author zhouwenxiang
 */
public abstract class AbstractWrappedStatefulTunnel extends AbstractWrappedSingleTunnel implements WrappedStatefulTunnel {

    @Override
    public TunnelTip push(String receiver, String msg) {
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
    @Override
    public TunnelTip pushWhenConnected(String receiver, String msg) {
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
