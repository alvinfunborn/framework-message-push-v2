package com.alvin.framework.message.push.v2.network.valve;

import com.alvin.framework.message.push.v2.substance.model.ValveTip;
import com.alvin.framework.message.push.v2.network.model.WrappedMessage;
import com.alvin.framework.message.push.v2.network.receiver.Receiver;
import com.alvin.framework.message.push.v2.network.rule.AbstractWrappedRule;
import com.alvin.framework.message.push.v2.network.tunnel.AbstractWrappedTunnel;

/**
 * datetime 2020/1/21 12:38
 *
 * @author zhouwenxiang
 */
public interface WrappedValve {

    ValveTip control(Receiver receiver, WrappedMessage message, AbstractWrappedTunnel tunnel);

    boolean support(AbstractWrappedRule rule);
}
