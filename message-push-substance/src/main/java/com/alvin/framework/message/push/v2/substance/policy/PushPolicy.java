package com.alvin.framework.message.push.v2.substance.policy;

import com.alvin.framework.message.push.v2.substance.trigger.PushTrigger;
import lombok.Data;

/**
 * datetime 2020/1/15 17:47
 *
 * @author zhouwenxiang
 */
@Data
public class PushPolicy {

    protected PushTunnelPolicy tunnelPolicy;
    protected PushTrigger trigger;
}
