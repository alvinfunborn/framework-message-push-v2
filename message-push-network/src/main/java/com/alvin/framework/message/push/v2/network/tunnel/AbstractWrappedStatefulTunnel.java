package com.alvin.framework.message.push.v2.network.tunnel;

import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.network.model.WrappedMessage;
import com.alvin.framework.message.push.v2.network.receiver.Receiver;
import com.alvin.framework.message.push.v2.network.valve.AbstractWrappedValve;

/**
 * datetime 2020/1/21 11:40
 *
 * @author zhouwenxiang
 */
public abstract class AbstractWrappedStatefulTunnel extends AbstractWrappedSingleTunnel implements WrappedStatefulTunnel {

    @Override
    public TunnelTip push(Receiver receiver, WrappedMessage msg) {
        for (AbstractWrappedValve valve : pusher.getValves()) {
            ValveTip valveTip = valve.control(receiver, msg, this);
            if (!valveTip.isOk()) {
                return TunnelTip.blocked(valveTip);
            }
        }
        return doPush(receiver, msg.getData());
    }

    /**
     * check connection before pushing
     * @param msg data
     * @return TunnelResult
     */
    @Override
    public TunnelTip pushWhenConnected(Receiver receiver, WrappedMessage msg) {
        for (AbstractWrappedValve valve : pusher.getValves()) {
            ValveTip valveTip = valve.control(receiver, msg, this);
            if (!valveTip.isOk()) {
                return TunnelTip.blocked(valveTip);
            }
        }
        if (connected(receiver)) {
            return doPush(receiver, msg.getData());
        }
        return TunnelTip.notConnected();
    }
}
