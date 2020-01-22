package com.alvin.framework.message.push.v2.wrapper.valve;

import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.wrapper.model.WrappedMessage;
import com.alvin.framework.message.push.v2.wrapper.receiver.Receiver;
import com.alvin.framework.message.push.v2.wrapper.rule.AbstractWrappedRule;
import com.alvin.framework.message.push.v2.wrapper.tunnel.AbstractWrappedTunnel;

/**
 * datetime 2020/1/21 12:38
 *
 * @author zhouwenxiang
 */
public interface WrappedValve {

    ValveTip control(Receiver receiver, WrappedMessage message, AbstractWrappedTunnel tunnel);

    boolean support(AbstractWrappedRule rule);
}
