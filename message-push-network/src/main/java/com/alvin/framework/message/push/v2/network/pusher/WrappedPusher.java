package com.alvin.framework.message.push.v2.network.pusher;

import com.alvin.framework.message.push.v2.network.model.WrappedMessage;
import com.alvin.framework.message.push.v2.network.receiver.Receiver;
import com.alvin.framework.message.push.v2.network.tunnel.AbstractWrappedTunnel;

/**
 * datetime 2020/1/18 21:17
 *
 * @author zhouwenxiang
 */
public interface WrappedPusher {

    void onStart();

    void add(Receiver receiver, WrappedMessage message, AbstractWrappedTunnel wrappedTunnel, boolean head);

    void onConnect(AbstractWrappedTunnel wrappedTunnel, Receiver receiver);

    void reportReceipt(String messageId);

}
