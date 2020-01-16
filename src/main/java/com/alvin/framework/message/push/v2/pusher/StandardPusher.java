package com.alvin.framework.message.push.v2.pusher;

import com.alvin.framework.message.push.v2.factory.TunnelFactory;
import com.alvin.framework.message.push.v2.model.Message;
import com.alvin.framework.message.push.v2.tunnel.*;

/**
 * datetime 2020/1/16 11:05
 *
 * @author zhouwenxiang
 */
public class StandardPusher implements Pusher {

    private TunnelFactory tunnelFactory;

    @Override
    public void onInit() {
    }

    @Override
    public void onConnect(AbstractStatefulTunnel tunnel) {

    }

    @Override
    public void addToTunnelQueue(Message message, AbstractSingleTunnel singleTunnel, boolean head) {

    }

    @Override
    public void addToTunnelQueue(Message message, CombinedStatelessTunnel combinedStatelessTunnel, boolean head) {

    }

    @Override
    public void addToTunnelQueue(Message message, CombinedStatefulTunnel combinedStatefulTunnel, boolean head) {

    }

    @Override
    public void reportReceipt(String receiver, AbstractTunnel tunnel, String messageId) {

    }
}
