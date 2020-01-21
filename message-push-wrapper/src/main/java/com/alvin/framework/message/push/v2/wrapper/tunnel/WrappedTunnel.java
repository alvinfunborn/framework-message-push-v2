package com.alvin.framework.message.push.v2.wrapper.tunnel;

import com.alvin.framework.message.push.v2.substance.model.TunnelTip;

/**
 * datetime 2020/1/18 22:36
 *
 * @author zhouwenxiang
 */
public interface WrappedTunnel {

    /**
     * push msg
     *
     * @param msg msg
     */
    TunnelTip doPush(String receiver, String msg);
}
