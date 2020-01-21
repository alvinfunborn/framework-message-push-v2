package com.alvin.framework.message.push.v2.substance.rule;

import com.alvin.framework.message.push.v2.substance.model.Business;
import lombok.Getter;

/**
 * datetime 2020/1/21 14:41
 *
 * @author zhouwenxiang
 */
@Getter
public class AbstractRuleOnBusiness implements RuleOnBusiness {

    protected Business biz;
}
