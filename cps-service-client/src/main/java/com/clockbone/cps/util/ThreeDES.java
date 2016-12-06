package com.clockbone.cps.util;



import javax.crypto.SecretKey;


/**
 * Created by qinjun on 2016/11/11.
 */
public class ThreeDES {

    /**
     * 获取密钥
     * @param key 字符串
     * @return 密钥
     */
    public static SecretKey getKey(String key) throws Exception {
      return null;
    }


    public static final String ECB = "DES/ECB/PKCS5Padding";
    public static final String CBC = "DES/CBC/PKCS5Padding";
    private static final String CHARSET = "utf-8";


    /**
     * CBC模式加密
     *
     * @param value 原文
     * @param key   密钥
     * @return 密文
     */

    public static String encryptWithCBC(String value, String key) throws Exception {
        return "";
    }

    /**
     * CBC模式解密
     *
     * @param value 密文
     * @param key   密钥
     * @return 原文
     */

    public static String decryptWithCBC(String value, String key) throws Exception {
       return "";
    }
}
