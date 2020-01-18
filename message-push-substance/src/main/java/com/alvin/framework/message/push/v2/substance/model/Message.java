package com.alvin.framework.message.push.v2.substance.model;

import com.alvin.framework.message.push.v2.substance.policy.RetryablePushPolicy;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * datetime 2020/1/15 17:44
 *
 * @author zhouwenxiang
 */
@Data
public class Message {

    private String id;
    private String data;
    private RetryablePushPolicy policy;
    private final AtomicInteger tryTimes = new AtomicInteger(0);

    public int markTried() {
        return this.tryTimes.addAndGet(1);
    }

    public boolean retryable() {
        return policy.getRetryPolicy().getRetry() >= tryTimes.get();
    }
}
