package com.alvin.framework.message.push.v2.wrapper.tunnel;

import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.recorder.AbstractTunnelRecorder;
import com.alvin.framework.message.push.v2.substance.valve.AbstractValve;

import java.util.ArrayList;
import java.util.List;

/**
 * datetime 2020/1/18 22:38
 *
 * @author zhouwenxiang
 */
// todo wrapped valve, tunnel 统一
public class AbstractWrappedTunnel {

    protected String name;
    protected List<AbstractValve> valves;

    public void assembleValve(AbstractValve valve) {
        if (valves == null) {
            valves = new ArrayList<>();
        }
        valve.registerTunnel(this);
        valves.add(valve);
    }

    public abstract TunnelTip push(String msg);

}
