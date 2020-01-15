package com.alvin.framework.message.push.v2.model;

import lombok.Data;

/**
 * datetime 2020/1/15 17:44
 *
 * @author zhouwenxiang
 */
@Data
public class Message {

    private String id;
    private String receiver;
    private String data;
    private PushPolicy policy;
}
