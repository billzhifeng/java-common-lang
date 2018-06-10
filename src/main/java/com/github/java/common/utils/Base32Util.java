package com.github.java.common.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.lang3.StringUtils;

import com.github.java.common.base.NameConstant;

/**
 * @Description: BASE32编码 http://tomeko.net/online_tools/base32.php?lang=en
 */
public class Base32Util {
    public static Base32 base32 = new Base32();

    public static String encode(String s) throws UnsupportedEncodingException {
        return base32.encodeToString(s.getBytes(NameConstant.UTF8));
    }

    public static String encodeNoEqualSign(String s) throws UnsupportedEncodingException {
        return base32.encodeToString(s.getBytes(NameConstant.UTF8)).replaceAll("=", "");
    }

    public static String decode(String s) throws UnsupportedEncodingException {
        byte[] bArray = base32.decode(s);
        return new String(bArray, NameConstant.UTF8);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String s = "aaaaaa,\\  /.<>?;'\\\\:\"|[]{]}abcdefghijklmnopqrstuvwxyz!@#$%^&*()_+1234567890-=`~我们是abc";
        System.out.println(encode(s));
        System.out.println(encodeNoEqualSign(s));
        System.out.println(decode(encodeNoEqualSign(s)));
        System.out.println(StringUtils.equals(decode(encodeNoEqualSign(s)), s));
    }

}
