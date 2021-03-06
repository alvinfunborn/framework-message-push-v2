package com.alvin.framework.message.push.v2.substance.executor;

import java.time.LocalDateTime;

/**
 * datetime 2020/1/16 16:12
 *
 * @author zhouwenxiang
 */
public interface Executor {

    void execute(Runnable r);

    void execute(Runnable r, boolean first);

    void executeOnSchedule(Runnable r, LocalDateTime time);
}
