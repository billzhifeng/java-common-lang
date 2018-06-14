package com.github.java.common.base;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 */
public abstract class Printable implements Serializable {

    /**
     * @see Object#toString()
     */
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public String buildMDCKey() {
        return "";
    }

}
