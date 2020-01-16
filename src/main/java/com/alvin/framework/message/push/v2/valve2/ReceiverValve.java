package com.alvin.framework.message.push.v2.valve2;

import com.alvin.framework.message.push.v2.model.ValveTip;
import com.alvin.framework.message.push.v2.valve.Valve;

/**
 * datetime 2020/1/15 20:10
 *
 * @author zhouwenxiang
 */
public interface ReceiverValve extends Valve {

    ValveTip control(String receiver);
}
