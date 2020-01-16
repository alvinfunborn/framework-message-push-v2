package com.alvin.framework.message.push.v2.factory;

import com.alvin.framework.message.push.v2.tunnel.AbstractTunnel;

import java.util.List;

/**
 * datetime 2020/1/16 15:40
 *
 * @author zhouwenxiang
 */
public interface TunnelFactory {

    List<AbstractTunnel> list();
}
