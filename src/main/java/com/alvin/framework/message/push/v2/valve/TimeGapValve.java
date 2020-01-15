package com.alvin.framework.message.push.v2.valve;

import com.alvin.framework.message.push.v2.handle.TimeGapHandle;
import lombok.Data;

/**
 * datetime 2020/1/15 20:14
 *
 * @author zhouwenxiang
 */
@Data
public class TimeGapValve implements Valve {

    protected TimeGapHandle handle;
}
