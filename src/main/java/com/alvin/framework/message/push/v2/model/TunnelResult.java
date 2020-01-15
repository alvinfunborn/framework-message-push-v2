package com.alvin.framework.message.push.v2.model;

import com.alvin.framework.message.push.v2.model.enums.TunnelResultEnum;
import com.alvin.framework.message.push.v2.model.enums.TunnelResultStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * datetime 2020/1/15 17:04
 *
 * @author zhouwenxiang
 */
@AllArgsConstructor
@Data
public class TunnelResult {

    private TunnelResultStatusEnum status;
    private String result;

    public static TunnelResult ofSuccess() {
        return new TunnelResult(TunnelResultStatusEnum.SUCCESS, null);
    }

    public static TunnelResult ofFailed(String result) {
        return new TunnelResult(TunnelResultStatusEnum.FAILED, result);
    }

    public static TunnelResult ofFailed(TunnelResultEnum resultEnum) {
        return new TunnelResult(TunnelResultStatusEnum.FAILED, resultEnum.getDesc());
    }

    public boolean isSuccessful() {
        return status == TunnelResultStatusEnum.SUCCESS;
    }
}
