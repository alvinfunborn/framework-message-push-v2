package com.alvin.framework.message.push.v2.executor;

/**
 * datetime 2020/1/16 16:12
 *
 * @author zhouwenxiang
 */
public interface Executor {

    void execute(Runnable r);

    void executeInOrder(Runnable r);

    void executeOnSchedule(Runnable r);
}
