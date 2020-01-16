package com.alvin.framework.message.push.v2.recorder;

import java.time.LocalDateTime;

/**
 * datetime 2020/1/15 22:41
 *
 * @author zhouwenxiang
 */
public interface TunnelRecorder {

    LocalDateTime lastSuccessTime();

    LocalDateTime lastSuccessTime(long rindex);

    LocalDateTime lastAttemptTime();

    LocalDateTime lastAttemptTime(long rindex);

}
