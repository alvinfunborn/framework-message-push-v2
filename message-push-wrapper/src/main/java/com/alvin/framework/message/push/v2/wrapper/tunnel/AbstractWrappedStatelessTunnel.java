package com.alvin.framework.message.push.v2.wrapper.tunnel;

import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.wrapper.model.WrappedMessage;
import com.alvin.framework.message.push.v2.wrapper.receiver.Receiver;
import com.alvin.framework.message.push.v2.wrapper.valve.AbstractWrappedValve;

/**
 * datetime 2020/1/22 14:17
 *
 * @author zhouwenxiang
 */
public abstract class AbstractWrappedStatelessTunnel extends AbstractWrappedTunnel {
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
}
