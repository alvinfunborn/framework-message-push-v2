package com.alvin.framework.message.push.v2.wrapper.pusher;

import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;
import com.alvin.framework.message.push.v2.wrapper.model.WrappedMessage;

/**
 * datetime 2020/1/18 21:17
 *
 * @author zhouwenxiang
 */
public interface WrappedPusher {

    void onStart();

    void add(WrappedMessage message, AbstractTunnel tunnel, boolean head);

    void onConnect(AbstractTunnel tunnel, String receiver);

    void reportReceipt(String id, String receiver, String tunnelName);

}
