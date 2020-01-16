package com.alvin.framework.message.push.v2.tunnel;

import com.alvin.framework.message.push.v2.model.TunnelTip;
import com.alvin.framework.message.push.v2.model.ValveTip;
import com.alvin.framework.message.push.v2.valve.Valve;

/**
 * datetime 2020/1/16 10:55
 *
 * @author zhouwenxiang
 */
public abstract class AbstractStatelessTunnel extends AbstractSingleTunnel {

    /**
     * check connection before pushing
     * @param msg data
     * @return TunnelResult
     */
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
}
