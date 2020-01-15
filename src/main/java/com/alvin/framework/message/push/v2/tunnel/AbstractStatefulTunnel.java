package com.alvin.framework.message.push.v2.tunnel;

import com.alvin.framework.message.push.v2.model.TunnelTip;
import com.alvin.framework.message.push.v2.model.enums.TunnelTipEnum;

/**
 * datetime 2020/1/15 15:51
 *
 * @author zhouwenxiang
 */
public abstract class AbstractStatefulTunnel extends AbstractSingleTunnel {

    /**
     * if receiver connected to this tunnel
     *
     * @param receiver receiver
     * @return true if connected
     */
    abstract boolean connected(String receiver);

    /**
     * check connection before pushing
     * @param receiver receiver
     * @param msg data
     * @return TunnelResult
     */
    public TunnelTip pushOnConnected(String receiver, String msg) {
        if (connected(receiver)) {
            return push(receiver, msg);
        }
        return TunnelTip.error(TunnelTipEnum.NOT_CONNECTED);
    }
}
