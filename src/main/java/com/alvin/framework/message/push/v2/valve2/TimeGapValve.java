package com.alvin.framework.message.push.v2.valve2;

import com.alvin.framework.message.push.v2.handle.TimeGapQuota;
import com.alvin.framework.message.push.v2.valve.Valve;
import lombok.Data;

/**
 * datetime 2020/1/15 20:14
 *
 * @author zhouwenxiang
 */
@Data
public class TimeGapValve implements Valve {

    protected TimeGapQuota handle;
}
