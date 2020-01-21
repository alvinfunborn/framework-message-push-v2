package com.alvin.framework.message.push.v2.substance.queue;

import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.pusher.GeneralMessageQueuePusher;
import com.alvin.framework.message.push.v2.substance.pusher.OrderedMessageQueuePusher;
import com.alvin.framework.message.push.v2.substance.pusher.StatefulMessageQueuePusher;

/**
 * datetime 2020/1/21 10:17
 *
 * @author zhouwenxiang
 */
public abstract class AbstractClusterMessageQueue implements MessageQueue {

    protected String name;
    private AbstractSingleMessageQueue orderedMessageQueue;
    private AbstractSingleMessageQueue statefulMessageQueue;
    private AbstractSingleMessageQueue generalMessageQueue;

    public AbstractClusterMessageQueue(String name, AbstractSingleMessageQueue orderedMessageQueue, AbstractSingleMessageQueue statefulMessageQueue, AbstractSingleMessageQueue generalMessageQueue) {
        this.name = name;
        this.orderedMessageQueue = orderedMessageQueue;
        this.statefulMessageQueue = statefulMessageQueue;
        this.generalMessageQueue = generalMessageQueue;
    }

    @Override
    public void add(Message message, boolean head) {
        if (message.getPolicy().getTunnelPolicy().isOrdered()) {
            orderedMessageQueue.add(message, head);
            orderedMessageQueue.getPusher().start();
        } else if (message.getPolicy().getTunnelPolicy().isStateful() && statefulMessageQueue != null) {
            statefulMessageQueue.add(message, head);
            statefulMessageQueue.getPusher().start();
        } else {
            generalMessageQueue.add(message, head);
            generalMessageQueue.getPusher().start();
        }
    }

    public Message popOrderedMessage() {
        return orderedMessageQueue.pop();
    }

    public Message popStatefulMessage() {
        return orderedMessageQueue.pop();
    }

    public Message popGeneralMessage() {
        return orderedMessageQueue.pop();
    }

    public void bindOrderedMessagePusher(OrderedMessageQueuePusher pusher) {
        orderedMessageQueue.bindPusher(pusher);
    }

    public void bindStatefulMessagePusher(StatefulMessageQueuePusher pusher) {
        statefulMessageQueue.bindPusher(pusher);
    }

    public void bindGeneralMessagePusher(GeneralMessageQueuePusher pusher) {
        generalMessageQueue.bindPusher(pusher);
    }
}
