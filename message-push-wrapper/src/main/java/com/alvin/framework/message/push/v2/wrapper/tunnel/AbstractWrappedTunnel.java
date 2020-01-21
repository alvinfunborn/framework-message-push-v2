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
public abstract class AbstractWrappedTunnel implements WrappedTunnel {

    protected String name;


    public abstract TunnelTip push(String receiver, String msg);

}
