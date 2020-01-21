package com.alvin.framework.message.push.v2.substance.queue;

import com.alvin.framework.message.push.v2.substance.model.Message;

/**
 * datetime 2020/1/21 10:19
 *
 * @author zhouwenxiang
 */
public interface SingleMessageQueue extends MessageQueue {

    Message pop();
}
