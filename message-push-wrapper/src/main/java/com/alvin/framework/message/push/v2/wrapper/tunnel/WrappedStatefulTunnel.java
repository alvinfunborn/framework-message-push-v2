package com.alvin.framework.message.push.v2.wrapper.tunnel;

import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.tunnel.Stateful;

/**
 * datetime 2020/1/18 22:37
 *
 * @author zhouwenxiang
 */
public interface WrappedStatefulTunnel extends WrappedTunnel, Stateful {

    boolean connected(String receiver);

    TunnelTip pushWhenConnected(String receiver, String msg);
}
