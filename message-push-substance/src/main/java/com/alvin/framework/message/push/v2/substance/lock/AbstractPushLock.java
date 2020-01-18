package com.alvin.framework.message.push.v2.substance.lock;

/**
 * datetime 2020/1/18 18:24
 *
 * @author zhouwenxiang
 */
public abstract class AbstractPushLock implements PushLock {

    protected String name;

    public abstract boolean tryLock(String name);

    public abstract void unlock(String name);

    @Override
    public final boolean tryLock() {
        return tryLock(name);
    }

    @Override
    public final void unlock() {
        unlock(name);
    }
}
