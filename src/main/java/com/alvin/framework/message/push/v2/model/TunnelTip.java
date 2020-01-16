package com.alvin.framework.message.push.v2.model;

import com.alvin.framework.message.push.v2.model.enums.TunnelTipEnum;
import com.alvin.framework.message.push.v2.model.enums.TunnelTipCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

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
    private LocalDateTime suggestTime;

    public static TunnelTip ok() {
        return new TunnelTip(TunnelTipCodeEnum.OK, null, null);
    }

    public static TunnelTip error(String result) {
        return new TunnelTip(TunnelTipCodeEnum.ERROR, result, null);
    }

    public static TunnelTip error(TunnelTipEnum resultEnum) {
        return new TunnelTip(TunnelTipCodeEnum.ERROR, resultEnum.getDesc(), null);
    }

    public static TunnelTip notConnected() {
        return new TunnelTip(TunnelTipCodeEnum.NOT_CONNECTED, null, null);
    }

    public static TunnelTip blocked(ValveTip valveTip) {
        return new TunnelTip(TunnelTipCodeEnum.ERROR, valveTip.getTip(), valveTip.getSuggestTime());
    }

    public boolean isOk() {
        return code == TunnelTipCodeEnum.OK;
    }
}
