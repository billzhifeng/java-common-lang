package com.github.java.common.utils;

import java.security.MessageDigest;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.github.java.common.base.NameConstant;

/**
 * @Description: MD5加签验签
 */
public class MD5 {
    // 用来将字节转换成 16 进制表示的字符
    static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static void main(String[] args) {
        //System.out.println(sign("我们" + "aaa", "UTF-8"));
        // 8472c72b5cd2441ed154de9ba2e8deb0
        //System.out.println(getSHA1("1213sdvgfdvnfdbn.fdmv.fm29ru390r43fj4opfj", "UTF-8"));

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 90000; i++) {
            getMD5(UUID.randomUUID().toString(), "UTF-8");
        }
        System.out.println((double) (System.currentTimeMillis() - startTime) / 1000);
    }

    /**
     * @param text 需要签名的字符串
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String input_charset) {
        return getMD5(text, input_charset);
    }

    /**
     * 签名字符串
     * 
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String input_charset) {
        if (StringUtils.isBlank(text)) {
            return false;
        }
        if (StringUtils.isBlank(sign)) {
            return false;
        }
        if (getMD5(text, input_charset).equals(sign)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 对一段String生成MD5加密信息
     * 
     * @param message 要加密的String
     * @return 生成的MD5信息
     */
    public static String getMD5(String message, String input_charset) {
        try {
            MessageDigest md = MessageDigest.getInstance(NameConstant.MD5);
            byte[] b;
            b = md.digest(message.getBytes(input_charset));

            return byteToHexString(b);
        } catch (Throwable t) {
            throw new RuntimeException("Failed to MD5 digest for message:" + message, t);
        }
    }

    /**
     * 对一段String生成SHA1加密信息
     * 
     * @param message
     * @param input_charset
     * @return
     */
    public static String getSHA1(String message, String input_charset) {
        try {
            MessageDigest md = MessageDigest.getInstance(NameConstant.SHA1);
            md.update(message.getBytes(input_charset));
            return byteToHexStringByLen(md.digest());
        } catch (Throwable e) {
            throw new RuntimeException("Failed to SHA1 digest for message:" + message, e);
        }
    }

    /**
     * 把byte[]数组转换成十六进制字符串表示形式
     * 
     * @param tmp 要转换的byte[]
     * @return 十六进制字符串表示形式
     */
    private static String byteToHexString(byte[] tmp) {
        // 用字节表示就是 16 个字节
        char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
        // 所以表示成 16 进制需要 32 个字符
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
            // 转换成 16 进制字符的转换
            byte byte0 = tmp[i]; // 取第 i 个字节
            str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
            // >>> 为逻辑右移，将符号位一起右移
            str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }

        return new String(str);
    }

    public static String byteToHexStringByLen(byte[] tmp) {
        // 用字节表示就是 16 个字节
        char str[] = new char[tmp.length * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
        // 所以表示成 16 进制需要 32 个字符
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < tmp.length; i++) { // 从第一个字节开始，对 MD5 的每一个字节
            // 转换成 16 进制字符的转换
            byte byte0 = tmp[i]; // 取第 i 个字节
            str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
            // >>> 为逻辑右移，将符号位一起右移
            str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }

        return new String(str);
    }

}
