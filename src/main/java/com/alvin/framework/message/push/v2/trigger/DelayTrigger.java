package com.alvin.framework.message.push.v2.trigger;

import lombok.Data;

/**
 * datetime 2020/1/16 9:57
 *
 * @author zhouwenxiang
 */
@Data
public class DelayTrigger implements PushTrigger {

    private long mills;
}
