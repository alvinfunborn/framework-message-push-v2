package com.alvin.framework.message.push.v2.wrapper.model;

import com.alvin.framework.message.push.v2.substance.executor.Executor;
import com.alvin.framework.message.push.v2.substance.lock.AbstractPushLock;
import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.pusher.StandardPusher;
import com.alvin.framework.message.push.v2.substance.queue.AbstractMessageQueue;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractStatefulTunnel;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;
import com.alvin.framework.message.push.v2.wrapper.factory.TunnelFactory;
import com.alvin.framework.message.push.v2.wrapper.pusher.WrappedPusher;
import com.alvin.framework.message.push.v2.wrapper.queue.WrappedMessageQueue;

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
    public void add(WrappedMessage message, AbstractTunnel tunnel, boolean head) {
        doAdd(message, tunnel, head);
    }

    @Override
    public void onConnect(AbstractTunnel tunnel, String receiver) {
        Set<String> biz = messageQueue.bizToTrigger(receiver, tunnel.getName());
        biz.forEach(b -> start(receiver, b, tunnel));
    }

    @Override
    public void reportReceipt(String id, String receiver, String tunnelName) {
        messageQueue.reportReceipt(id, receiver, tunnelName);
    }

    private void doAdd(WrappedMessage message, AbstractTunnel tunnel, boolean head) {
        StandardPusher pusher = genenrate(message.getReceiver(), message.getBiz(), tunnel);
        pusher.add(message, head);
    }

    private void start(String receiver, String biz, AbstractTunnel tunnel) {
        StandardPusher pusher = genenrate(receiver, biz, tunnel);
        pusher.start();
    }

    // todo cache
    private StandardPusher genenrate(String receiver, String biz, AbstractTunnel tunnel) {
        StandardPusher pusher = new StandardPusher(null, executor, lock, new AbstractMessageQueue() {
            @Override
            public void add(Message message, boolean head) {

            }

            @Override
            public Message pop() {
                return null;
            }

            @Override
            public void onPushOk(Message message) {

            }

            @Override
            public void onPushAttempt(Message message) {

            }

            @Override
            public void onPushError(Message message, TunnelTip tip) {

            }

            @Override
            public void reportReceipt(String id) {

            }

            @Override
            public boolean consumeReceipt(String id) {
                return false;
            }
        }, new AbstractMessageQueue() {
            @Override
            public void add(Message message, boolean head) {

            }

            @Override
            public Message pop() {
                return null;
            }

            @Override
            public void onPushOk(Message message) {

            }

            @Override
            public void onPushAttempt(Message message) {

            }

            @Override
            public void onPushError(Message message, TunnelTip tip) {

            }

            @Override
            public void reportReceipt(String id) {

            }

            @Override
            public boolean consumeReceipt(String id) {
                return false;
            }
        }, new AbstractMessageQueue() {
            @Override
            public void add(Message message, boolean head) {

            }

            @Override
            public Message pop() {
                return null;
            }

            @Override
            public void onPushOk(Message message) {

            }

            @Override
            public void onPushAttempt(Message message) {

            }

            @Override
            public void onPushError(Message message, TunnelTip tip) {

            }

            @Override
            public void reportReceipt(String id) {

            }

            @Override
            public boolean consumeReceipt(String id) {
                return false;
            }
        });
        return pusher;
    }

    private AbstractTunnel generateTunnel(AbstractTunnel tunnel, String receiver, String biz) {
        if (tunnel instanceof AbstractStatefulTunnel) {

        }
    }
}
