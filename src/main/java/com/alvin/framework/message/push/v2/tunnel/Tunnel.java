package com.alvin.framework.message.push.v2.tunnel;

import com.alvin.framework.message.push.v2.model.TunnelResult;

/**
 * datetime 2020/1/15 15:46
 *
 * @author zhouwenxiang
 */
public interface Tunnel {

    /**
     * push msg
     *
     * @param receiver receiver
     * @param msg msg
     */
    TunnelResult push(String receiver, String msg);
}
