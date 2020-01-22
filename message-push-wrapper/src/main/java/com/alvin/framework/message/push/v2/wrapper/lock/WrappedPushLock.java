package com.alvin.framework.message.push.v2.wrapper.lock;

import com.alvin.framework.message.push.v2.wrapper.receiver.Receiver;
import com.alvin.framework.message.push.v2.wrapper.tunnel.AbstractWrappedTunnel;

/**
 * datetime 2020/1/22 15:21
 *
 * @author zhouwenxiang
 */
public interface WrappedPushLock {

    boolean tryLock(Receiver receiver, AbstractWrappedTunnel tunnel);

    void unlock(Receiver receiver, AbstractWrappedTunnel tunnel);
}
