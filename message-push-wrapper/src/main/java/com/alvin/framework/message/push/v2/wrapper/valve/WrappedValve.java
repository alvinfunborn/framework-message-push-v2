package com.alvin.framework.message.push.v2.wrapper.valve;

import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.wrapper.rule.WrappedRule;

/**
 * datetime 2020/1/21 12:38
 *
 * @author zhouwenxiang
 */
public interface WrappedValve {

    ValveTip control(String receiver, String biz);

    boolean support(WrappedRule rule);
}
