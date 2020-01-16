package com.alvin.framework.message.push.v2.queue;

import com.alvin.framework.message.push.v2.model.Message;

/**
 * datetime 2020/1/16 15:30
 *
 * @author zhouwenxiang
 */
public interface MessageQueue {

    void add(Message message, boolean head);

    Message pop();
}
