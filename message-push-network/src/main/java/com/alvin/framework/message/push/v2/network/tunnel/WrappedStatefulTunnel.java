package com.alvin.framework.message.push.v2.network.tunnel;

import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.network.model.WrappedMessage;
import com.alvin.framework.message.push.v2.network.receiver.Receiver;

/**
 * datetime 2020/1/18 22:37
 *
 * @author zhouwenxiang
 */
public interface WrappedStatefulTunnel extends WrappedTunnel {

    boolean connected(Receiver receiver);

    TunnelTip pushWhenConnected(Receiver receiver, WrappedMessage msg);
}
