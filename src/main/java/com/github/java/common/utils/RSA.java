package com.github.java.common.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.java.common.base.NameConstant;

public class RSA {

    private final static Logger logger                 = LoggerFactory.getLogger(RSA.class);

    // public static final String SIGN_ALGORITHMS = "MD5withRSA";
    public static final String  SIGN_ALGORITHMS        = "SHA1withRSA";

    public static final String  SHA256_SIGN_ALGORITHMS = "SHA256withRSA";

    public static final String  SHA512_SIGN_ALGORITHMS = "SHA512withRSA";

    /**
     * 使用{@code RSA}方式对字符串进行签名
     *
     * @param content 需要加签名的数据
     * @param privateKey {@code RSA}的私钥
     * @param charset 数据的编码方式
     * @return 返回签名信息
     */
    public static String sign(String content, String privateKey, String charset) {
        return sign(content, privateKeyConversion(privateKey, charset), charset);
    }

    /**
     * 使用{@code RSA}方式对字符串进行签名
     *
     * @param content 需要加签名的数据
     * @param _privateKey {@code RSA}的私钥
     * @param charset 数据的编码方式
     * @return 返回签名信息
     */
    public static String sign(String content, PrivateKey _privateKey, String charset) {
        return commonSign(getSignature(SIGN_ALGORITHMS, null), content, _privateKey, charset);
    }

    /**
     * 使用{@code RSA}方式对字符串进行签名
     * 
     * @param algorithms 加密算法
     * @param content 需要加签名的数据
     * @param privateKey {@code RSA}的私钥
     * @param charset 数据的编码方式
     * @return 返回签名信息
     */
    public static String sign(String algorithms, String content, String privateKey, String charset) {
        return commonSign(getSignature(algorithms, null), content, privateKeyConversion(privateKey, charset), charset);
    }

    /**
     * 使用{@code RSA}方式对字符串进行签名
     * 
     * @param algorithms 加密算法
     * @param content 需要加签名的数据
     * @param _privateKey {@code RSA}的私钥
     * @param charset 数据的编码方式
     * @return 返回签名信息
     */
    public static String sign(String algorithms, String content, PrivateKey _privateKey, String charset) {
        return commonSign(getSignature(algorithms, null), content, _privateKey, charset);
    }

    /**
     * RSA验签公用方法
     * 
     * @param algorithms 计算签名算法
     * @param provider 算法提供者
     * @param content 需要加签名的数据
     * @param privateKey {@code RSA}的私钥
     * @param charset 数据的编码方式
     * @return 返回签名信息
     */
    public static String sign(String algorithms, String provider, String content, String privateKey, String charset) {
        return commonSign(getSignature(algorithms, provider), content, privateKeyConversion(privateKey, charset),
                charset);
    }

    /**
     * RSA验签公用方法
     * 
     * @param algorithms 计算签名算法
     * @param provider 算法提供者
     * @param content 需要加签名的数据
     * @param _privateKey {@code RSA}的私钥
     * @param charset 数据的编码方式
     * @return 返回签名信息
     */
    public static String sign(String algorithms, String provider, String content, PrivateKey _privateKey,
                              String charset) {
        return commonSign(getSignature(algorithms, provider), content, _privateKey, charset);
    }

    /**
     * 将String形式的key转化为PrivateKey对象
     * 
     * @param privateKey String形式的key
     */
    private static PrivateKey privateKeyConversion(String privateKey, String charset) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
                    Base64.getDecoder().decode(privateKey.getBytes(charset)));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            return keyf.generatePrivate(keySpec);
        } catch (Exception ex) {
            logger.error("转化privateKey失败: {}", ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * RSA验签公用方法
     * 
     * @param signature 签名算法对象
     * @param content 需要加签名的数据
     * @param _privateKey {@code RSA}的私钥
     * @param charset 数据的编码方式
     * @return 返回签名信息
     */
    private static String commonSign(Signature signature, String content, PrivateKey _privateKey, String charset) {
        try {
            signature.initSign(_privateKey);
            signature.update(content.getBytes(charset));
            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (Exception ex) {
            logger.error("RSA ERROR: {}", ex.getMessage(), ex);
            return "";
        }
    }

    /**
     * 将Strin类型的公钥转化为PublicKey对象
     * 
     * @param publicKey String类型的公钥
     * @return PublicKey的公钥
     */
    private static PublicKey publicKeyConversion(String publicKey, String charset) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                    Base64.getDecoder().decode(publicKey.getBytes(charset)));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(keySpec);
        } catch (Exception ex) {
            logger.error("转化公钥失败: {}", ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * 使用{@code RSA}方式对签名信息进行验证
     *
     * @param content 需要加签名的数据
     * @param sign 签名信息
     * @param publicKey {@code RSA}的公钥
     * @param charset 数据的编码方式
     * @return 是否验证通过。{@code True}表示通过
     */
    public static boolean verify(String content, String sign, final String publicKey, String charset) {
        return verify(content, sign, publicKeyConversion(publicKey, charset), charset);
    }

    /**
     * 使用{@code RSA}方式对签名信息进行验证
     *
     * @param content 需要加签名的数据
     * @param sign 签名信息
     * @param _publicKey {@code RSA}的公钥
     * @param charset 数据的编码方式
     * @return 是否验证通过。{@code True}表示通过
     */
    public static boolean verify(String content, String sign, PublicKey _publicKey, String charset) {
        return commonVerify(getSignature(SIGN_ALGORITHMS, null), content, sign, _publicKey, charset);
    }

    /**
     * 通用验证逻辑
     * 
     * @param algorithms 算法
     * @param content 需要加签名的数据
     * @param sign 签名信息
     * @param publicKey {@code RSA}的公钥
     * @param charset 数据的编码方式
     * @return 是否验证通过。{@code True}表示通过
     */
    public static boolean verify(String algorithms, String content, String sign, String publicKey, String charset) {
        return commonVerify(getSignature(algorithms, null), content, sign, publicKeyConversion(publicKey, charset),
                charset);
    }

    /**
     * 通用验证逻辑
     * 
     * @param algorithms 算法
     * @param content 需要加签名的数据
     * @param sign 签名信息
     * @param _publicKey {@code RSA}的公钥
     * @param charset 数据的编码方式
     * @return 是否验证通过。{@code True}表示通过
     */
    public static boolean verify(String algorithms, String content, String sign, PublicKey _publicKey, String charset) {
        return commonVerify(getSignature(algorithms, null), content, sign, _publicKey, charset);
    }

    /**
     * 通用验证逻辑
     * 
     * @param algorithms 算法
     * @param provider 算法提供者
     * @param content 需要加签名的数据
     * @param sign 签名信息
     * @param publicKey {@code RSA}的公钥
     * @param charset 数据的编码方式
     * @return 是否验证通过。{@code True}表示通过
     */
    public static boolean verify(String algorithms, String provider, String content, String sign, String publicKey,
                                 String charset) {
        return commonVerify(getSignature(algorithms, provider), content, sign, publicKeyConversion(publicKey, charset),
                charset);
    }

    /**
     * 通用验证逻辑
     * 
     * @param algorithms 算法
     * @param provider 算法提供者
     * @param content 需要加签名的数据
     * @param sign 签名信息
     * @param _publicKey {@code RSA}的公钥
     * @param charset 数据的编码方式
     * @return 是否验证通过。{@code True}表示通过
     */
    public static boolean verify(String algorithms, String provider, String content, String sign, PublicKey _publicKey,
                                 String charset) {
        return commonVerify(getSignature(algorithms, provider), content, sign, _publicKey, charset);
    }

    /**
     * 通用验证逻辑
     * 
     * @param signature 签名算法对象
     * @param content 需要加签名的数据
     * @param sign 签名信息
     * @param _publicKey {@code RSA}的公钥
     * @param charset 数据的编码方式
     * @return 是否验证通过。{@code True}表示通过
     */
    private static boolean commonVerify(Signature signature, String content, String sign, PublicKey _publicKey,
                                        String charset) {
        try {
            signature.initVerify(_publicKey);
            signature.update(content.getBytes(charset));
            return signature.verify(Base64.getDecoder().decode(sign.getBytes(charset)));
        } catch (Exception ex) {
            logger.error("RSA ERROR: {}", ex.getMessage(), ex);
            return false;
        }
    }

    /**
     * 获取签名算法对象
     */
    private static Signature getSignature(String algorithms, String provider) {
        try {
            //算法提供者为空
            return StringUtils.isBlank(provider) ? Signature.getInstance(algorithms)
                    : Signature.getInstance(algorithms, provider);
        } catch (Exception e) {
            logger.error("RSA ERROR:", e);
        }
        return null;
    }

    private static void testRSA256(String privateKey, String alipayPublicKey, String plainText) {
        String sign = RSA.sign(RSA.SHA256_SIGN_ALGORITHMS, plainText, privateKey, NameConstant.UTF8);
        System.out.println(sign);
        boolean b = RSA.verify(RSA.SHA256_SIGN_ALGORITHMS, plainText, sign, alipayPublicKey, NameConstant.UTF8);
        System.out.println(b);

    }
}
