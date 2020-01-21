package com.alvin.framework.message.push.v2.substance.recorder;

import com.alvin.framework.message.push.v2.substance.model.Business;
import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;

import java.time.LocalDateTime;

/**
 * datetime 2020/1/15 22:41
 *
 * @author zhouwenxiang
 */
public interface TunnelRecorder {

    LocalDateTime lastSuccessTime(long num);
    LocalDateTime lastSuccessTime(Business biz, long num);

    LocalDateTime lastAttemptTime(long num);
    LocalDateTime lastAttemptTime(Business biz, long num);

    void recordError(Message message, TunnelTip tip);

    void recordSuccess(Message message);

    void recordAttempt(Message message);
}
