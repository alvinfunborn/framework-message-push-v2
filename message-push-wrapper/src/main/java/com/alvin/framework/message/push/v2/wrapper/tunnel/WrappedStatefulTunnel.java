package com.alvin.framework.message.push.v2.wrapper.tunnel;

import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.wrapper.model.WrappedMessage;
import com.alvin.framework.message.push.v2.wrapper.receiver.Receiver;

/**
 * datetime 2020/1/18 22:37
 *
 * @author zhouwenxiang
 */
public interface WrappedStatefulTunnel extends WrappedTunnel {

    boolean connected(Receiver receiver);

    TunnelTip pushWhenConnected(Receiver receiver, WrappedMessage msg);
}
