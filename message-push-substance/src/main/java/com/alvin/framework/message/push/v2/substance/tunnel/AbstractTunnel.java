package com.alvin.framework.message.push.v2.substance.tunnel;

import com.alvin.framework.message.push.v2.substance.business.BusinessFactory;
import com.alvin.framework.message.push.v2.substance.model.Message;
import com.alvin.framework.message.push.v2.substance.model.TunnelTip;
import com.alvin.framework.message.push.v2.substance.recorder.AbstractTunnelRecorder;
import com.alvin.framework.message.push.v2.substance.valve.AbstractValve;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * datetime 2020/1/15 22:22
 *
 * @author zhouwenxiang
 */
@Data
public abstract class AbstractTunnel implements Tunnel {

    protected String name;
    protected AbstractTunnelRecorder recorder;
    protected BusinessFactory businessFactory;
    protected List<AbstractValve> valves;

    public AbstractTunnel(String name, AbstractTunnelRecorder recorder, List<AbstractValve> valves) {
        this.name = name;
        this.recorder = recorder;
        this.valves = valves;
    }

    public void assembleValve(AbstractValve valve) {
        if (valves == null) {
            valves = new ArrayList<>();
        }
        valve.bindTunnel(this);
        valves.add(valve);
    }

    public void bindRecorder(AbstractTunnelRecorder recorder) {
        recorder.registerTunnel(this);
        this.recorder = recorder;
    }

    public abstract TunnelTip push(Message msg);
}
