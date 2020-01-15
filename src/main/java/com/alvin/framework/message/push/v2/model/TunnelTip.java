package com.alvin.framework.message.push.v2.model;

import com.alvin.framework.message.push.v2.model.enums.TunnelTipEnum;
import com.alvin.framework.message.push.v2.model.enums.TunnelTipCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * datetime 2020/1/15 17:04
 *
 * @author zhouwenxiang
 */
@AllArgsConstructor
@Data
public class TunnelTip {

    private TunnelTipCodeEnum code;
    private String tip;

    public static TunnelTip ok() {
        return new TunnelTip(TunnelTipCodeEnum.OK, null);
    }

    public static TunnelTip error(String result) {
        return new TunnelTip(TunnelTipCodeEnum.ERROR, result);
    }

    public static TunnelTip error(TunnelTipEnum resultEnum) {
        return new TunnelTip(TunnelTipCodeEnum.ERROR, resultEnum.getDesc());
    }

    public boolean isSuccessful() {
        return code == TunnelTipCodeEnum.OK;
    }
}
