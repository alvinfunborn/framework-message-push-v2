package com.alvin.framework.message.push.v2.valve;

import com.alvin.framework.message.push.v2.model.ValveTip;
import com.alvin.framework.message.push.v2.rule.Rule;

/**
 * datetime 2020/1/15 19:56
 *
 * @author zhouwenxiang
 */
public interface Valve {

    ValveTip control();

    boolean executable(Rule rule);

}
