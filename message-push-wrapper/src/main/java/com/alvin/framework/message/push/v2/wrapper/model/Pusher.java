package com.alvin.framework.message.push.v2.wrapper.model;

import lombok.Data;

/**
 * datetime 2020/1/18 21:53
 *
 * @author zhouwenxiang
 */
@Data
public class Pusher {

    private String receiver;
    private String biz;
    private String tunnelName;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        Pusher o = (Pusher) obj;
        return o.toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
