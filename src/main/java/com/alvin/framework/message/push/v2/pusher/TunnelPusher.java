package com.alvin.framework.message.push.v2.pusher;

import com.alvin.framework.message.push.v2.model.Message;

/**
 * datetime 2020/1/16 15:46
 *
 * @author zhouwenxiang
 */
public interface TunnelPusher {

    void start();

    void add(Message message, boolean head);

}
