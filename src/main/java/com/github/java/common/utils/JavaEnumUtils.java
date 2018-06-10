package com.github.java.common.utils;

import org.apache.commons.lang3.EnumUtils;

/**
 */
public class JavaEnumUtils extends EnumUtils {

    public static String getEnumName(Enum<?> enumObj) {
        if (enumObj == null) {
            return null;
        } else {
            return enumObj.name();
        }

    }
}
