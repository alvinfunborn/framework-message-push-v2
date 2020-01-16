package com.alvin.framework.message.push.v2.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * datetime 2020/1/15 17:06
 *
 * @author zhouwenxiang
 */
@AllArgsConstructor
@Getter
public enum TunnelTipCodeEnum {

    UNKNOWN(1),
    OK(2),
    BLOCKED(3),
    NOT_CONNECTED(4),
    ERROR(5),
    ;

    private int code;
}
