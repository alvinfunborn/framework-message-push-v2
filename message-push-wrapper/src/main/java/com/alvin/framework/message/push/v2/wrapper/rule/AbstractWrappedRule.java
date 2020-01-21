package com.alvin.framework.message.push.v2.wrapper.rule;

import java.util.Set;

/**
 * datetime 2020/1/21 14:08
 *
 * @author zhouwenxiang
 */
public abstract class AbstractWrappedRule implements WrappedRule {

    protected boolean eachReceiver;
    protected Set<String> receiverBundle;
}
