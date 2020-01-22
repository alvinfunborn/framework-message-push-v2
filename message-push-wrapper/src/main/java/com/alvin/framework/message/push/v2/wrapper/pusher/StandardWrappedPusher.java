package com.alvin.framework.message.push.v2.wrapper.pusher;

import com.alvin.framework.message.push.v2.substance.executor.Executor;
import com.alvin.framework.message.push.v2.wrapper.lock.WrappedPushLock;
import com.alvin.framework.message.push.v2.wrapper.receiver.Receiver;
import com.alvin.framework.message.push.v2.wrapper.recorder.WrappedPushRecorder;
import com.alvin.framework.message.push.v2.wrapper.model.Pusher;
import com.alvin.framework.message.push.v2.wrapper.model.WrappedMessage;
import com.alvin.framework.message.push.v2.wrapper.queue.WrappedMessageQueue;
import com.alvin.framework.message.push.v2.wrapper.tunnel.AbstractWrappedTunnel;

import java.util.HashSet;
import java.util.Set;

/**
 * datetime 2020/1/18 21:23
 *
 * @author zhouwenxiang
 */
public class StandardWrappedPusher implements WrappedPusher {

    private WrappedMessageQueue queue;
    private WrappedPushRecorder recorder;
    private Executor executor;
    private WrappedPushLock lock;

    public StandardWrappedPusher(WrappedMessageQueue queue, WrappedPushRecorder recorder, Executor executor, WrappedPushLock lock) {
        this.queue = queue;
        this.recorder = recorder;
        this.executor = executor;
        this.lock = lock;
    }

    @Override
    public void onStart() {
        Set<Pusher> pushers = queue.pushersToTrigger();
        assemblePusher(pushers).forEach(AbstractTunnelPusher::start);
    }

    @Override
    public void add(Receiver receiver, WrappedMessage message, AbstractWrappedTunnel tunnel, boolean head) {
        doAdd(receiver, message, tunnel, head);
        if (message.getPolicy().getTunnelPolicy().isOrdered()) {
            assembleOrderedMessagePusher(receiver, tunnel).start();
        } else if (message.getPolicy().getTunnelPolicy().isStateful()) {
            assembleStatefulMessagePusher(receiver, tunnel).start();
        } else {
            assembleGeneralMessagePusher(receiver, tunnel).start();
        }
    }

    @Override
    public void onConnect(AbstractWrappedTunnel tunnel, Receiver receiver) {
        assembleOrderedMessagePusher(receiver, tunnel).start();
        assembleStatefulMessagePusher(receiver, tunnel).start();
    }

    @Override
    public void reportReceipt(String messageId) {
        queue.reportReceipt(messageId);
    }

    private void doAdd(Receiver receiver, WrappedMessage message, AbstractWrappedTunnel wrappedTunnel, boolean head) {
        queue.add(receiver, message, wrappedTunnel, head);
    }

    private Set<AbstractTunnelPusher> assemblePusher(Set<Pusher> pusher) {
        Set<AbstractTunnelPusher> pushers = new HashSet<>();
        pusher.forEach(p -> {
            pushers.add(assembleOrderedMessagePusher(p.getReceiver(), p.getTunnel()));
            pushers.add(assembleStatefulMessagePusher(p.getReceiver(), p.getTunnel()));
            pushers.add(assembleGeneralMessagePusher(p.getReceiver(), p.getTunnel()));
        });
        return pushers;
    }

    private OrderedMessageTunnelPusher assembleOrderedMessagePusher(Receiver receiver, AbstractWrappedTunnel tunnel) {
        return new OrderedMessageTunnelPusher(receiver, tunnel, queue, recorder, lock, executor);
    }

    private StatefulMessageTunnelPusher assembleStatefulMessagePusher(Receiver receiver, AbstractWrappedTunnel tunnel) {
        return new StatefulMessageTunnelPusher(receiver, tunnel, queue, recorder, lock, executor);
    }

    private GeneralMessageTunnelPusher assembleGeneralMessagePusher(Receiver receiver, AbstractWrappedTunnel tunnel) {
        return new GeneralMessageTunnelPusher(receiver, tunnel, queue, recorder, lock, executor);
    }
}
