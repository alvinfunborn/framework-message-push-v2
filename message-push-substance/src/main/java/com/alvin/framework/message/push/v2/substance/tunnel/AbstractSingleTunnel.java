package com.alvin.framework.message.push.v2.substance.tunnel;

import com.alvin.framework.message.push.v2.substance.recorder.AbstractTunnelRecorder;
import com.alvin.framework.message.push.v2.substance.valve.AbstractValve;

import java.util.List;

/**
 * datetime 2020/1/15 17:41
 *
 * @author zhouwenxiang
 */
public abstract class AbstractSingleTunnel extends AbstractTunnel {

    public AbstractSingleTunnel(String name, AbstractTunnelRecorder recorder, List<AbstractValve> valves) {
        super(name, recorder, valves);
    }
}
