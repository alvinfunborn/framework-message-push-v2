package com.alvin.framework.message.push.v2.wrapper.pusher;

import com.alvin.framework.message.push.v2.wrapper.model.WrappedMessage;
import com.alvin.framework.message.push.v2.wrapper.tunnel.AbstractWrappedTunnel;

/**
 * datetime 2020/1/18 21:17
 *
 * @author zhouwenxiang
 */
public interface WrappedPusher {

    void onStart();

    void add(WrappedMessage message, AbstractWrappedTunnel wrappedTunnel, boolean head);

    void onConnect(AbstractWrappedTunnel wrappedTunnel, String receiver);

    void reportReceipt(String id, String receiver, String tunnelName);

}
