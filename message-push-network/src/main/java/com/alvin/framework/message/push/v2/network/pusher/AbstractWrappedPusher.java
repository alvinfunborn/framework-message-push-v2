package com.alvin.framework.message.push.v2.network.pusher;

import com.alvin.framework.message.push.v2.substance.business.BusinessFactory;
import com.alvin.framework.message.push.v2.network.queue.WrappedMessageQueue;
import com.alvin.framework.message.push.v2.network.receiver.ReceiverFactory;
import com.alvin.framework.message.push.v2.network.recorder.WrappedPushRecorder;
import com.alvin.framework.message.push.v2.network.tunnel.TunnelFactory;
import com.alvin.framework.message.push.v2.network.valve.AbstractWrappedValve;
import lombok.Getter;

import java.util.List;

/**
 * datetime 2020/1/22 11:34
 *
 * @author zhouwenxiang
 */
@Getter
public class AbstractWrappedPusher {

    protected WrappedPushRecorder recorder;
    protected List<AbstractWrappedValve> valves;
    protected WrappedMessageQueue queue;
    protected BusinessFactory businessFactory;
    protected ReceiverFactory receiverFactory;
    protected TunnelFactory tunnelFactory;
}
