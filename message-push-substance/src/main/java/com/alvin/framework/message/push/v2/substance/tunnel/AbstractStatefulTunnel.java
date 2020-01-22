package com.alvin.framework.message.push.v2.substance.tunnel;

import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.substance.recorder.AbstractTunnelRecorder;
import com.alvin.framework.message.push.v2.substance.valve.AbstractValve;
import com.alvin.framework.message.push.v2.substance.valve.Valve;

import java.util.List;

/**
 * datetime 2020/1/15 15:51
 *
 * @author zhouwenxiang
 */
public abstract class AbstractStatefulTunnel extends AbstractSingleTunnel implements StatefulTunnel {

    public AbstractStatefulTunnel(String name, AbstractTunnelRecorder recorder, List<AbstractValve> valves) {
        super(name, recorder, valves);
    }

    @Override
    public TunnelTip push(Message msg) {
        for (Valve valve : valves) {
            ValveTip valveTip = valve.control(msg);
            if (!valveTip.isOk()) {
                return TunnelTip.blocked(valveTip);
            }
        }
        return doPush(msg.getData());
    }

    /**
     * check connection before pushing
     * @param msg data
     * @return TunnelResult
     */
    @Override
    public TunnelTip pushWhenConnected(Message msg) {
        for (Valve valve : valves) {
            ValveTip valveTip = valve.control(msg);
            if (!valveTip.isOk()) {
                return TunnelTip.blocked(valveTip);
            }
        }
        if (connected()) {
            return doPush(msg.getData());
        }
        return TunnelTip.notConnected();
    }
}
