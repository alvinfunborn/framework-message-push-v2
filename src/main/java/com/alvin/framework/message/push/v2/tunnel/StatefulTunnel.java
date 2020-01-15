package com.alvin.framework.message.push.v2.tunnel;

import com.alvin.framework.message.push.v2.model.TunnelResult;
import com.alvin.framework.message.push.v2.model.enums.TunnelResultEnum;

/**
 * datetime 2020/1/15 15:51
 *
 * @author zhouwenxiang
 */
interface StatefulTunnel extends SingleTunnel {

    /**
     * if receiver connected to this tunnel
     *
     * @param receiver receiver
     * @return true if connected
     */
    boolean connected(String receiver);

    /**
     * check connection before pushing
     * @param receiver receiver
     * @param msg data
     * @return TunnelResult
     */
    default TunnelResult pushOnConnected(String receiver, String msg) {
        if (connected(receiver)) {
            return push(receiver, msg);
        }
        return TunnelResult.ofFailed(TunnelResultEnum.NOT_CONNECTED);
    }
}
