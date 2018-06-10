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

    public static void main(String[] args) {
        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJegSSYYo2zQCUZ8+FAkOTKZCVOczZaEwmdjvBkVRdhGndwtDs0fPifM1DaBzGhcAYauzJU/zkgGMSBvvHKWnI266fBFwqUTDOVBD2WzMWrtlPUmJrN3y/lNrSPb+RAiyqR+i1LC8ym5P++sGVj5ByRfeKKfb3AHLEG9wWiIrFWRAgMBAAECgYBjSua8GORkbHO2gj4GFUKTnN4bsBJ9oCDCnfBeqKydbdkQhz0rHGuSmrLZovRd3ups7Wkxz5/hUJS5PhK1I2YO8Ca76rSscsFNrxRp3i40QXOQcYN5Wmg/vb2FAhrBzlyvWYqw3WskuhTJGdls01zhp/cXP1CmNoD3oUGgFF7WAQJBAOsQJKabt21G35DnxkU/03ldHmtg0KpuYDZbUz6OCjrK8cKXM4acHVQ0rUlrO64BmmZwSRGxXLGES4dX+242HyECQQClIaGqzd0rbsyhbTX29Zp8exCjKfxGApncUvxMgppvIIZ6flEXLZKo22apK+Biu/jz20PbRrTrbJ8CQ6XXrZhxAkA0+r93+bK+43FyJHsQkpEkUnOPuhNtxlDTD4RUngj6NVGGDTQky7zkhLblzFB4KI5XNkmRvkrpC6tGRvM5ZONhAkB2CUpEi47ywuqm4SfX9cuN+3HzD8nySV2t7evGzPyTPs6htLVGOh9BcEM73c8xBGh591vT3ADq3+PxfGWJe98hAkEAkh7VuEiA4ESh3zZn6VXrEAvbR8mZvodRIP5G8doNYx4hnW+iJf6oMPmUxxHAGraj2xBg/APtS54Ypx9p/DUBRg==";
        String alipayPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCXoEkmGKNs0AlGfPhQJDkymQlTnM2WhMJnY7wZFUXYRp3cLQ7NHz4nzNQ2gcxoXAGGrsyVP85IBjEgb7xylpyNuunwRcKlEwzlQQ9lszFq7ZT1Jiazd8v5Ta0j2/kQIsqkfotSwvMpuT/vrBlY+QckX3iin29wByxBvcFoiKxVkQIDAQAB";
        String plainText = "body=途虎234测试&buyer_email=15021399156&buyer_id=2088702451461824&exterface=create_direct_pay_by_user&extra_common_param=PMRHAYLZNVSW45CJNZZXI4TVMN2GS33OJFSCEORCGE3DKNBCFQRG65LUIJUXUTTPEI5CEMRRGEYTCMJQGA4SELBCOJSXI5LSNZKXE3BCHIRGQ5DUOA5C6L3XO53S45DVNB2S4Y3OEIWCE3LFOJRWQYLOORHG6IR2EIZDAOBYGUYTCOBTGA4TMNZYGAZCELBCMNUGC3TOMVWEG33EMURDUISDJBAU4TSFJRPUCTCJKBAVSX2EJFJEKQ2UEJ6Q&is_success=T&notify_id=RqPnCoPT3K9%2Fvwbh3InYwe9W%2ByiTfZpa8khRkj%2Fc%2FEhIf3nNxezcq05P%2BeV1XNOCNGEX&notify_time=2017-06-22 11:03:21&notify_type=trade_status_sync&out_trade_no=1187&payment_type=1&seller_email=lantuzhifubao@tuhu.cn&seller_id=2088511830967802&subject=途虎234测试&total_fee=0.01&trade_no=2017062221001004820221467519&trade_status=TRADE_SUCCESS";
        String sign = RSA.sign(plainText, privateKey, NameConstant.UTF8);
        System.out.println(sign);
        boolean b = RSA.verify(plainText, sign, alipayPublicKey, NameConstant.UTF8);
        System.out.println(b);

        System.out.println("----------------------");
        testRSA256(privateKey, alipayPublicKey, plainText);
    }

    private static void testRSA256(String privateKey, String alipayPublicKey, String plainText) {
        String sign = RSA.sign(RSA.SHA256_SIGN_ALGORITHMS, plainText, privateKey, NameConstant.UTF8);
        System.out.println(sign);
        boolean b = RSA.verify(RSA.SHA256_SIGN_ALGORITHMS, plainText, sign, alipayPublicKey, NameConstant.UTF8);
        System.out.println(b);

    }
}
