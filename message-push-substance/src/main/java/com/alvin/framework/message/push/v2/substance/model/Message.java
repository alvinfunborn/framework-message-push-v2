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

    protected String id;
    protected String data;
    protected RetryablePushPolicy policy;
    protected final AtomicInteger tryTimes = new AtomicInteger(0);

    public int markTried() {
        return this.tryTimes.addAndGet(1);
    }

    public boolean retryable() {
        return policy.getRetryPolicy().getRetry() >= tryTimes.get();
    }
}
