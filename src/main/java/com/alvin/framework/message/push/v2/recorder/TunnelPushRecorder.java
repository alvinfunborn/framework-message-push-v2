package com.alvin.framework.message.push.v2.recorder;

import java.time.LocalDateTime;

/**
 * datetime 2020/1/15 22:41
 *
 * @author zhouwenxiang
 */
public interface TunnelPushRecorder {

    long successfulPushCount(LocalDateTime start, LocalDateTime end);

    long successfulPushCount(LocalDateTime start, LocalDateTime end, String receiver);

    LocalDateTime successfulPushLately();

    LocalDateTime successfulPushLately(String receiver);
}
