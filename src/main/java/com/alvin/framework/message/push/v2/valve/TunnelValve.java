package com.alvin.framework.message.push.v2.valve;

import com.alvin.framework.message.push.v2.model.ValveTip;

/**
 * datetime 2020/1/15 20:09
 *
 * @author zhouwenxiang
 */
public interface TunnelValve extends Valve {

    ValveTip control();
}
