package com.alvin.framework.message.push.v2.substance.pusher;

import com.alvin.framework.message.push.v2.substance.model.Message;

/**
 * datetime 2020/1/18 17:14
 *
 * @author zhouwenxiang
 */
public interface SingleMessageQueuePusher extends Pusher {

    void pushContinuously();

    boolean pushContinuously(Message message);

    boolean postPush(Message message);

    void reportReceipt(String id);

}
