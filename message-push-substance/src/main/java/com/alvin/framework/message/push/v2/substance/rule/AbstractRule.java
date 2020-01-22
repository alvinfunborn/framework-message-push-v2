package com.alvin.framework.message.push.v2.substance.rule;

import lombok.Getter;

/**
 * datetime 2020/1/22 9:49
 *
 * @author zhouwenxiang
 */
@Getter
public abstract class AbstractRule implements Rule {

    protected RuleScopeOfPush pushScope;
    protected RuleScopeOfBiz bizScope;
}
