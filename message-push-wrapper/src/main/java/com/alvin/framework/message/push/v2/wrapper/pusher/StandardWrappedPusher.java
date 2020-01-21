package com.alvin.framework.message.push.v2.wrapper.pusher;

import com.alvin.framework.message.push.v2.substance.executor.Executor;
import com.alvin.framework.message.push.v2.substance.lock.AbstractPushLock;
import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.pusher.StandardPusher;
import com.alvin.framework.message.push.v2.substance.queue.AbstractSingleMessageQueue;
import com.alvin.framework.message.push.v2.substance.recorder.AbstractTunnelRecorder;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractStatefulTunnel;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;
import com.alvin.framework.message.push.v2.substance.tunnel.Stateful;
import com.alvin.framework.message.push.v2.substance.tunnel.StatefulTunnel;
import com.alvin.framework.message.push.v2.wrapper.factory.TunnelFactory;
import com.alvin.framework.message.push.v2.wrapper.model.Pusher;
import com.alvin.framework.message.push.v2.wrapper.model.WrappedMessage;
import com.alvin.framework.message.push.v2.wrapper.pusher.WrappedPusher;
import com.alvin.framework.message.push.v2.wrapper.queue.WrappedMessageQueue;
import com.alvin.framework.message.push.v2.wrapper.tunnel.AbstractWrappedStatefulTunnel;
import com.alvin.framework.message.push.v2.wrapper.tunnel.AbstractWrappedTunnel;

import java.util.Set;

/**
 * datetime 2020/1/18 21:23
 *
 * @author zhouwenxiang
 */
public class StandardWrappedPusher implements WrappedPusher {

    private WrappedMessageQueue messageQueue;
    private TunnelFactory tunnelFactory;
    private Executor executor;
    private AbstractPushLock lock;

    @Override
    public void onStart() {
        Set<Pusher> pushers = messageQueue.pushersToTrigger();
        pushers.forEach(p -> start(p.getReceiver(), p.getBiz(), tunnelFactory.getByName(p.getTunnelName())));
    }

    @Override
    public void add(WrappedMessage message, AbstractWrappedTunnel wrappedTunnel, boolean head) {
        doAdd(message, wrappedTunnel, head);
    }

    @Override
    public void onConnect(AbstractWrappedTunnel wrappedTunnel, String receiver) {
        Set<String> biz = messageQueue.bizToTrigger(receiver, wrappedTunnel.getName());
        biz.forEach(b -> start(receiver, b, wrappedTunnel));
    }

    @Override
    public void reportReceipt(String id, String receiver, String tunnelName) {
        messageQueue.reportReceipt(id, receiver, tunnelName);
    }

    private void doAdd(WrappedMessage message, AbstractWrappedTunnel wrappedTunnel, boolean head) {
        StandardPusher pusher = genenrate(message.getReceiver(), message.getBiz(), wrappedTunnel);
        pusher.add(message, head);
    }

    private void start(String receiver, String biz, AbstractWrappedTunnel wrappedTunnel) {
        StandardPusher pusher = genenrate(receiver, biz, wrappedTunnel);
        pusher.start();
    }

    // todo cache
    private StandardPusher genenrate(String receiver, String biz, AbstractWrappedTunnel wrappedTunnel) {
        AbstractTunnel tunnel = generateTunnel(wrappedTunnel, receiver, biz);
        StandardPusher standardPusher = new StandardPusher();
    }

    private AbstractTunnel generateTunnel(AbstractWrappedTunnel wrappedTunnel, String receiver, String biz) {
        if (wrappedTunnel instanceof AbstractWrappedStatefulTunnel) {
            return new AbstractStatefulTunnel() {
                @Override
                public boolean connected() {
                    return ((AbstractWrappedStatefulTunnel) wrappedTunnel).connected(receiver);
                }

                @Override
                public TunnelTip doPush(String msg) {
                    return wrappedTunnel.doPush(receiver, msg);
                }
            };
        } else if (wrappedTunnel instanceof AbstractWrappedStatelessTunnel) {
            return
        }
    }

    private AbstractTunnelRecorder generateTunnelRecorder() {

    }
}
