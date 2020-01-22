package com.alvin.framework.message.push.v2.network.pusher;

import com.alvin.framework.message.push.v2.substance.model.Message;

/**
 * datetime 2020/1/22 15:01
 *
 * @author zhouwenxiang
 */
public interface WrappedSingleMessageQueuePusher extends WrappedPusher {

    void pushContinuously();

    boolean pushContinuously(Message message);

    boolean postPush(Message message);

    void reportReceipt(String messageId);
}
