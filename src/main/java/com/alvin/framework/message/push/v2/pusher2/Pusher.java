package com.alvin.framework.message.push.v2.pusher2;

import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.tunnel.*;

/**
 * datetime 2020/1/16 10:51
 *
 * @author zhouwenxiang
 */
public interface Pusher {

    /**
     * run task after init
     */
    void onInit();

    /**
     * run onConnect when tunnel connected
     *
     * @param tunnel tunnel
     */
    void onConnect(AbstractStatefulTunnel tunnel);

    /**
     * push to queue. message will be pushed by this specific tunnel
     *
     * @param message message
     * @param singleTunnel tunnel that will push this msg
     * @param head if add to head
     */
    void addToTunnelQueue(Message message, AbstractSingleTunnel singleTunnel, boolean head);

    /**
     * push to queue. message will be pushed by one of the tunnel in tunnels
     *
     * @param message message
     * @param combinedStatelessTunnel tunnels
     * @param head if add to head
     */
    void addToTunnelQueue(Message message, CombinedStatelessTunnel combinedStatelessTunnel, boolean head);

    /**
     * push to queue. message will be pushed by first connected tunnel in tunnels
     *
     * @param message message
     * @param combinedStatefulTunnel tunnels
     * @param head if add to head
     */
    void addToTunnelQueue(Message message, CombinedStatefulTunnel combinedStatefulTunnel, boolean head);

    /**
     * report receipt
     *
     * @param receiver receiver
     * @param tunnel receive msg from which tunnel
     * @param messageId message id
     */
    void reportReceipt(String receiver, AbstractTunnel tunnel, String messageId);
}
