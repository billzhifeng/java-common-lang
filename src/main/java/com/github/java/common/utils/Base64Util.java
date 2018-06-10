package com.github.java.common.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 */
public class Base64Util {

    /**
     * 对给定的字符串进行base64解码操作
     */
    public static String decode(String inputData, String charset) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.decodeBase64(inputData.getBytes(charset)), charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对给定的字符串进行base64加密操作
     */
    public static String encode(String inputData, String charset) {
        try {
            if (null == inputData) {
                return null;
            }
            return new String(Base64.encodeBase64(inputData.getBytes(charset)), charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(Base64Util.encode("A", "UTF-8"));
    }

}
