package com.alvin.framework.message.push.v2.substance.pusher;

import com.alvin.framework.message.push.v2.substance.model.Message;

/**
 * datetime 2020/1/16 15:46
 *
 * @author zhouwenxiang
 */
public interface Pusher {

    void start();

    void add(Message message, boolean head);

    void reportReceipt(String messageId);
}
