package com.alvin.framework.message.push.v2.network.pusher;

import com.alvin.framework.message.push.v2.network.model.WrappedMessage;

/**
 * datetime 2020/1/22 14:02
 *
 * @author zhouwenxiang
 */
public interface TunnelPusher {

    void start();

    void add(WrappedMessage message, boolean head);

    void pushContinuously();

    boolean pushContinuously(WrappedMessage message);

    boolean postPush(WrappedMessage message);

    void reportReceipt(String id);

}
