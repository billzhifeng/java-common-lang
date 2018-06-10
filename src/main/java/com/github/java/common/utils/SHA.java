package com.github.java.common.utils;

import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;

import com.github.java.common.base.NameConstant;

public class SHA {

    public static void main(String[] args) {
        String text = "123456";
        //sha1 : 7c4a8d09ca3762af61e59520943dc26494f8941b
        //在git bash 验证 : echo -n "123456" | sha1sum.exe
        System.out.println(sha1(text, NameConstant.UTF8));
        //sha256 : echo -n "123456" | sha256sum
        //8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92
        System.out.println(sha256(text, NameConstant.UTF8));
    }

    private SHA() {
    }

    /**
     * 计算sha1取得16进制的摘要值
     */
    public static String sha1(String text, String encoding) {
        return commonSHA(NameConstant.SHA1, text, encoding);
    }

    /**
     * 计算sha256取得16进制的摘要值
     */
    public static String sha256(String text, String encoding) {
        return commonSHA(NameConstant.SHA256, text, encoding);
    }

    /**
     * @param algorithm 签名算法
     * @param text 要签名的文本
     * @param encoding 编码
     * @return 16进制的摘要值
     */
    private static String commonSHA(String algorithm, String text, String encoding) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.reset();
            md.update(text.getBytes(encoding));
            return MD5.byteToHexStringByLen(md.digest());
        } catch (Exception e) {
            throw new RuntimeException("Failed to " + algorithm + " digest for message:" + text, e);
        }
    }

    /**
     * 验证sha1值是否正确
     */
    public static boolean verifySHA1(String text, String encoding, String sign) {
        return commonVerify(NameConstant.SHA1, text, encoding, sign);
    }

    /**
     * 验证sha256值是否正确
     */
    public static boolean verifySHA256(String text, String encoding, String sign) {
        return commonVerify(NameConstant.SHA256, text, encoding, sign);
    }

    /**
     * @param algorithm 摘要算法
     * @param text 文本
     * @param encoding 编码方式
     * @param sign 验证的签名
     * @return true 表示正确
     */
    private static boolean commonVerify(String algorithm, String text, String encoding, String sign) {
        if (StringUtils.isBlank(sign) || StringUtils.isBlank(text)) {
            return false;
        }
        return sign.equalsIgnoreCase(commonSHA(algorithm, text, encoding));
    }

}
