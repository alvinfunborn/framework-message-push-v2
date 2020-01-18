package com.alvin.framework.message.push.v2.valve2;

import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.substance.valve.Valve;

/**
 * datetime 2020/1/15 20:09
 *
 * @author zhouwenxiang
 */
public interface TunnelValve extends Valve {

    ValveTip control();
}
