package com.alvin.framework.message.push.v2.recorder;

import java.time.LocalDateTime;

/**
 * datetime 2020/1/15 22:03
 *
 * @author zhouwenxiang
 */
public interface pushRecorder {

    long successfulPushCount(String name, LocalDateTime start, LocalDateTime end);

    long successfulPushCount(String name, LocalDateTime start, LocalDateTime end, String receiver);

    LocalDateTime successfulPushLately(String name);

    LocalDateTime successfulPushLately(String name, String receiver);

}
