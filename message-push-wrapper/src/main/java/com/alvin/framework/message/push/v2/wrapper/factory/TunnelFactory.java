package com.alvin.framework.message.push.v2.wrapper.factory;

import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;

import java.util.List;

/**
 * datetime 2020/1/18 21:30
 *
 * @author zhouwenxiang
 */
public interface TunnelFactory {

    void register(AbstractTunnel tunnel);

    AbstractTunnel getByName(String name);

    List<AbstractTunnel> tunnels();
}
