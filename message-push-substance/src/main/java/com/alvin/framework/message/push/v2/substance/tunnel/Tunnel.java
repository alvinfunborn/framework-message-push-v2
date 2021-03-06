package com.alvin.framework.message.push.v2.substance.tunnel;

import com.alvin.framework.message.push.v2.substance.model.TunnelTip;

/**
 * datetime 2020/1/15 15:46
 *
 * @author zhouwenxiang
 */
public interface Tunnel {

    /**
     * push msg
     *
     * @param msg msg
     */
    TunnelTip doPush(String msg);
}
