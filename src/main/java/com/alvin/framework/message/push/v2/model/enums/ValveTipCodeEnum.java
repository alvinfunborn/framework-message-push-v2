package com.alvin.framework.message.push.v2.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * datetime 2020/1/15 20:04
 *
 * @author zhouwenxiang
 */
@AllArgsConstructor
@Getter
public enum ValveTipCodeEnum {

    OK(2, null),
    ;

    private int code;
    private String tip;
}
