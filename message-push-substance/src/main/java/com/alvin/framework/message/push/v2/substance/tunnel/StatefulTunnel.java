package com.alvin.framework.message.push.v2.substance.tunnel;

import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;

/**
 * datetime 2020/1/21 11:35
 *
 * @author zhouwenxiang
 */
public interface StatefulTunnel extends Tunnel {

    boolean connected();

    TunnelTip pushWhenConnected(Message msg);
}
