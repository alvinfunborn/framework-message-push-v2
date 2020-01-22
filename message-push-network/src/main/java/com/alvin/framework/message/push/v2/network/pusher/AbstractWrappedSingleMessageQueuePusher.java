package com.alvin.framework.message.push.v2.network.pusher;

import com.alvin.framework.message.push.v2.substance.executor.Executor;
import com.alvin.framework.message.push.v2.substance.lock.AbstractPushLock;
import com.alvin.framework.message.push.v2.substance.queue.AbstractClusterMessageQueue;
import com.alvin.framework.message.push.v2.network.tunnel.AbstractWrappedTunnel;

/**
 * datetime 2020/1/22 15:02
 *
 * @author zhouwenxiang
 */
public abstract class AbstractWrappedSingleMessageQueuePusher implements WrappedSingleMessageQueuePusher {

    protected AbstractWrappedTunnel tunnel;
    protected AbstractClusterMessageQueue queue;
    protected Executor executor;
    protected AbstractPushLock lock;
}
