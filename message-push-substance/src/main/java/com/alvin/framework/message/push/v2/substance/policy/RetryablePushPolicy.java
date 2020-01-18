package com.alvin.framework.message.push.v2.substance.policy;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * datetime 2020/1/16 10:02
 *
 * @author zhouwenxiang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RetryablePushPolicy extends PushPolicy {

    private RetryPolicy retryPolicy;
}
