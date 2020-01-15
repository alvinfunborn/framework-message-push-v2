package com.alvin.framework.message.push.v2.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * datetime 2020/1/15 17:35
 *
 * @author zhouwenxiang
 */
@AllArgsConstructor
@Getter
public enum TunnelTipEnum {

    NO_TUNNEL_SUCCEED("no tunnel succeed"),
    NOT_CONNECTED("not connected"),
    ;

    private String desc;

}
