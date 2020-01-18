package com.alvin.framework.message.push.v2.substance.lock;

/**
 * datetime 2020/1/18 18:10
 *
 * @author zhouwenxiang
 */
public interface PushLock {

    boolean tryLock();

    void unlock();
}
