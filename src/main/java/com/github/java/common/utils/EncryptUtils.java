package com.github.java.common.utils;

public class EncryptUtils {

    /**
     *  加密 -- 转为ASCII，把ASCII加上对应得位数，以新得ASCII码为密文
     * @param value
     * @return
     */
    public static String encrypt(String value) {
        if(value ==null || value ==""){
            return null;
        }
        int value_asc[] = new int[value.length()];
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            value_asc[i] = (int)value.charAt(i)+i ;//转ASCII码加密
            sb.append((char) value_asc[i]);
        }
        return sb.toString();
    }

    /**
     *  解密
     * @param vul
     * @return
     */
    public static String decrypt(String vul) {
        if(vul ==null || vul ==""){
            return null;
        }
        int value_encr[] = new int[vul.length()];
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < vul.length(); i++) {
            value_encr[i] = (int) vul.charAt(i) - i;
            sb.append((char)value_encr[i]);
        }
        return sb.toString();
    }

    /**
     * 数据脱敏  中间加 * 号
     */
    public static String antianaphylaxis(String data ){
        if(data == null || data ==""){
            return null;
        }
        if (data.length() == 1) {
            return data + "*" ;
        }
        if(data.length()<8){
            return data.substring(0,2)+"***" ;
        }else{
            data = data.substring(0,3) + "****" + data.substring(data.length()-4,data.length());
            return data ;
        }
    }
}
