package com.alvin.framework.message.push.v2.substance.pusher;

import com.alvin.framework.message.push.v2.substance.queue.AbstractClusterMessageQueue;

/**
 * datetime 2020/1/22 11:34
 *
 * @author zhouwenxiang
 */
public abstract class AbstractPusher implements Pusher {

    protected AbstractClusterMessageQueue messageQueue;
}
