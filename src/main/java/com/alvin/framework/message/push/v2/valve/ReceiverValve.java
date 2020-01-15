package com.alvin.framework.message.push.v2.valve;

import com.alvin.framework.message.push.v2.model.ValveTip;

/**
 * datetime 2020/1/15 20:10
 *
 * @author zhouwenxiang
 */
public interface ReceiverValve extends Valve {

    ValveTip control(String receiver);
}
