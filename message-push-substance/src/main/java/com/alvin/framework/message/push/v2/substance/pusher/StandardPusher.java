package com.alvin.framework.message.push.v2.substance.pusher;

import com.alvin.framework.message.push.v2.substance.executor.Executor;
import com.alvin.framework.message.push.v2.substance.lock.AbstractPushLock;
import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.queue.AbstractClusterMessageQueue;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractStatefulTunnel;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;

/**
 * datetime 2020/1/16 15:47
 *
 * @author zhouwenxiang
 */
public class StandardPusher extends AbstractPusher {

    private OrderedMessageQueuePusher orderedMessageQueuePusher;
    private StatefulMessageQueuePusher statefulMessageQueuePusher;
    private GeneralMessageQueuePusher generalMessageQueuePusher;

    public StandardPusher(final AbstractTunnel tunnel,
                          final Executor executor,
                          final AbstractPushLock lock,
                          final AbstractClusterMessageQueue messageQueue) {
        this.messageQueue = messageQueue;
        this.orderedMessageQueuePusher = new OrderedMessageQueuePusher(tunnel, this.messageQueue, executor, lock);
        this.messageQueue.bindOrderedMessagePusher(orderedMessageQueuePusher);
        this.generalMessageQueuePusher = new GeneralMessageQueuePusher(tunnel, this.messageQueue, executor, lock);
        this.messageQueue.bindGeneralMessagePusher(generalMessageQueuePusher);
        if (tunnel instanceof AbstractStatefulTunnel) {
            this.statefulMessageQueuePusher = new StatefulMessageQueuePusher(tunnel, this.messageQueue, executor, lock);
            this.messageQueue.bindStatefulMessagePusher(statefulMessageQueuePusher);
        }
    }

    @Override
    public void start() {
        orderedMessageQueuePusher.start();
        statefulMessageQueuePusher.start();
        generalMessageQueuePusher.start();
    }

    @Override
    public void add(Message message, boolean head) {
        messageQueue.add(message, head);
    }

    @Override
    public void reportReceipt(String messageId) {
        messageQueue.reportReceipt(messageId);
    }
}
