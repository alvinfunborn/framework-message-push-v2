package com.alvin.framework.message.push.v2.substance.queue;

import com.alvin.framework.message.push.v2.substance.pusher.AbstractSingleMessageQueuePusher;
import lombok.Getter;

/**
 * datetime 2020/1/16 15:50
 *
 * @author zhouwenxiang
 */
@Getter
public abstract class AbstractSingleMessageQueue implements SingleMessageQueue {

    protected AbstractSingleMessageQueuePusher pusher;

    public void bindPusher(AbstractSingleMessageQueuePusher pusher) {
        this.pusher = pusher;
    }
}
