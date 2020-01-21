package com.alvin.framework.message.push.v2.substance.model;

import lombok.Data;

import java.util.Set;

/**
 * datetime 2020/1/21 14:38
 *
 * @author zhouwenxiang
 */
@Data
public class Business {

    private String name;
    private Set<Business> subBiz;
}
