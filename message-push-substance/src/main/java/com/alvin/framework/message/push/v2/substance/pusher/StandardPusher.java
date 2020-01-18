package com.alvin.framework.message.push.v2.substance.pusher;

import com.alvin.framework.message.push.v2.substance.executor.Executor;
import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.queue.AbstractMessageQueue;
import com.alvin.framework.message.push.v2.substance.tunnel.AbstractTunnel;

/**
 * datetime 2020/1/16 15:47
 *
 * @author zhouwenxiang
 */
public class StandardPusher implements Pusher {

    private OrderedMessageQueuePusher orderedMessageQueuePusher;
    private StatefulMessageQueuePusher statefulMessageQueuePusher;
    private GeneralMessageQueuePusher generalMessageQueuePusher;

    public StandardPusher(AbstractTunnel tunnel,
                          Executor executor,
                          AbstractMessageQueue orderedMessageQueue,
                          AbstractMessageQueue statefulMessageQueue,
                          AbstractMessageQueue generalMessageQueue) {
        this.orderedMessageQueuePusher = new OrderedMessageQueuePusher(tunnel, orderedMessageQueue, executor);
        this.statefulMessageQueuePusher = new StatefulMessageQueuePusher(tunnel, statefulMessageQueue, executor);
        this.generalMessageQueuePusher = new GeneralMessageQueuePusher(tunnel, generalMessageQueue, executor);
    }

    @Override
    public void start() {
        orderedMessageQueuePusher.start();
        statefulMessageQueuePusher.start();
        generalMessageQueuePusher.start();
    }

    @Override
    public void add(Message message, boolean head) {
        if (message.getPolicy().getTunnelPolicy().isOrdered()) {
            orderedMessageQueuePusher.add(message, head);
        } else if (message.getPolicy().getTunnelPolicy().isStateful()) {
            statefulMessageQueuePusher.add(message, head);
        } else {
            generalMessageQueuePusher.add(message, head);
        }
    }
}
