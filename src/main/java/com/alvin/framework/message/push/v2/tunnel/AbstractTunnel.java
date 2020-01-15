package com.alvin.framework.message.push.v2.tunnel;

import lombok.Data;

/**
 * datetime 2020/1/15 22:22
 *
 * @author zhouwenxiang
 */
@Data
public abstract class AbstractTunnel implements Tunnel {

    protected String name;
}
