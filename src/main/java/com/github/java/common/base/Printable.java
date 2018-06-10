package com.github.java.common.base;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 */
public abstract class Printable implements Serializable {

    private static final long serialVersionUID = 2953004129033219962L;

    /**
     * @see Object#toString()
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }

    public String buildMDCKey() {
        return "";
    }

}
