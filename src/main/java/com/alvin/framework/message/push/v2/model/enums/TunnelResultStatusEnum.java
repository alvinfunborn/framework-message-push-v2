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
public enum TunnelResultStatusEnum {

    UNKNOWN(1),
    SUCCESS(2),
    FAILED(3),
    ;

    private int code;
}
