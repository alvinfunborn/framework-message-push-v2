package com.alvin.framework.message.push.v2.substance.queue;

import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;

/**
 * datetime 2020/1/16 15:30
 *
 * @author zhouwenxiang
 */
public interface MessageQueue {

    void add(Message message, boolean head);

    void reportReceipt(String id);

    boolean consumeReceipt(String id);
}
