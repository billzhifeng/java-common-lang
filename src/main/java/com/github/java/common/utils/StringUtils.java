package com.github.java.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @Description StringUtils.java
 * @date 2017年6月30日 下午4:30:25
 */
public class StringUtils {

    public static Random random = new Random();

    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 产生一个随机字符串
     * 
     * @param range 可选字符
     * @param len 生成字符串的长度
     * @return
     */
    public static String genRandomStr(String range, int len) {
        if (isBlank(range) || len <= 0)
            return "";

        StringBuilder builder = new StringBuilder();
        char[] arr = range.toCharArray();
        for (int i = 0; i < len; i++) {
            builder.append(arr[random.nextInt(arr.length)]);
        }

        return builder.toString();
    }

    /**
     * 字符串转码
     * 
     * @param src
     * @param srcCode
     * @param targetCode
     * @return
     */
    public static String transcode(String src, String srcCode, String targetCode) {
        if (src == null) {
            return null;
        }
        try {
            return new String(src.getBytes(srcCode), targetCode);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
