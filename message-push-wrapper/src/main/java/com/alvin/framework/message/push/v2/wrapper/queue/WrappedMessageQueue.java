package com.alvin.framework.message.push.v2.wrapper.queue;

import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;
import com.alvin.framework.message.push.v2.wrapper.model.Pusher;
import com.alvin.framework.message.push.v2.wrapper.model.WrappedMessage;

import java.util.Set;

/**
 * datetime 2020/1/18 21:25
 *
 * @author zhouwenxiang
 */
public interface WrappedMessageQueue {

    void add(WrappedMessage message, AbstractTunnel tunnel, boolean head);

    WrappedMessage pop(String receiver, String biz, AbstractTunnel tunnel);

    Set<Pusher> pushersToTrigger();

    Set<String> bizToTrigger(String receiver, String tunnelName);

    void onSuccess(WrappedMessage message, AbstractTunnel tunnel);

    void onError(WrappedMessage message, AbstractTunnel tunnel);

    void onAttempt(WrappedMessage message, AbstractTunnel tunnel);

    void reportReceipt(String id, String receiver, String tunnelName);

}
