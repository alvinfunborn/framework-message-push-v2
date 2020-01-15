package com.alvin.framework.message.push.v2.handle;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * datetime 2020/1/15 20:35
 *
 * @author zhouwenxiang
 */
@EqualsAndHashCode
@Data
public class TimeWindowHandle extends TimeHandle {

    private long quantity;
}
