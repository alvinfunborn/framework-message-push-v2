package com.alvin.framework.message.push.v2.model;

import com.alvin.framework.message.push.v2.model.enums.ValveTipCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * datetime 2020/1/15 20:00
 *
 * @author zhouwenxiang
 */
@AllArgsConstructor
@Data
public class ValveTip {

    private ValveTipCodeEnum code;
    private String tip;

    public static ValveTip ok() {
        return new ValveTip(ValveTipCodeEnum.OK, null);
    }

    public boolean isSuccessful() {
        return code == ValveTipCodeEnum.OK;
    }
}
