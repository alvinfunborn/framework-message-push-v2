package com.alvin.framework.message.push.v2.substance.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * datetime 2020/1/15 20:00
 *
 * @author zhouwenxiang
 */
@AllArgsConstructor
@Data
public class ValveTip {

    private boolean block;
    private String tip;
    private LocalDateTime suggestTime;

    public static ValveTip ok() {
        return new ValveTip(false, null, null);
    }

    public static ValveTip block(String tip) {
        return new ValveTip(true, tip, null);
    }

    public static ValveTip block(String tip, LocalDateTime suggestTime) {
        if (suggestTime != null && suggestTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("suggest time before now");
        }
        return new ValveTip(true, tip, suggestTime);
    }

    public boolean isOk() {
        return !block;
    }
}
