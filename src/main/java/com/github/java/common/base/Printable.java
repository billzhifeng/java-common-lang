package com.github.java.common.base;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 */
public abstract class Printable implements Serializable {

    /**
     * @see Object#toString()
     */
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String buildMDCKey() {
        return "";
    }

}
