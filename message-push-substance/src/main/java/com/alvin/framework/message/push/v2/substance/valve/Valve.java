package com.alvin.framework.message.push.v2.substance.valve;

import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.substance.rule.Rule;

/**
 * datetime 2020/1/15 19:56
 *
 * @author zhouwenxiang
 */
public interface Valve {

    ValveTip control(Message msg);

    boolean support(Rule rule);

}
