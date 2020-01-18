package com.alvin.framework.message.push.v2.wrapper.model;

import com.alvin.framework.message.push.v2.substance.model.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * datetime 2020/1/18 21:19
 *
 * @author zhouwenxiang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WrappedMessage extends Message {

    protected String receiver;
    protected String biz;
}
