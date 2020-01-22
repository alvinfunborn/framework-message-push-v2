package com.alvin.framework.message.push.v2.network.queue;

import com.alvin.framework.message.push.v2.network.model.Pusher;
import com.alvin.framework.message.push.v2.network.model.WrappedMessage;
import com.alvin.framework.message.push.v2.network.receiver.Receiver;
import com.alvin.framework.message.push.v2.network.tunnel.AbstractWrappedTunnel;

import java.util.Set;

/**
 * datetime 2020/1/18 21:25
 *
 * @author zhouwenxiang
 */
public interface WrappedMessageQueue {

    void add(Receiver receiver, WrappedMessage message, AbstractWrappedTunnel tunnel, boolean head);

    WrappedMessage popOrderedMessage(Receiver receiver, AbstractWrappedTunnel tunnel);
    WrappedMessage popStatefulMessage(Receiver receiver, AbstractWrappedTunnel tunnel);
    WrappedMessage popGeneralMessage(Receiver receiver, AbstractWrappedTunnel tunnel);

    Set<Pusher> pushersToTrigger();

    void onSuccess(WrappedMessage message);

    void onError(WrappedMessage message);

    void onAttempt(WrappedMessage message);

    void reportReceipt(String messageId);

    boolean consumeReceipt(String messageId);

}
