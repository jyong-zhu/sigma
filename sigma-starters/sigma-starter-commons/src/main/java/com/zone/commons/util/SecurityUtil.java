package com.zone.commons.util;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;

/**
 * @Author: jianyong.zhu
 * @Date: 2021/3/13 4:25 下午
 * @Description:
 */
public final class SecurityUtil {

    private static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEqP0tF/mZ023YtVAm1KQghx9i+sRPYXCVGqKDTYo+1XmSGaQgrVMFfBdNd4DZbT16HWV+fc5Q7N09GAgghntpNZc4xd7wC2ug0exsKw6HmsvxKBU/+m6N/7mmnESDhdeIHcF4ia5KbSvz+IGFru3N3uO+cJysIAfjYnqDOWWwnwIDAQAB";
    private static final String PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMSo/S0X+ZnTbdi1UCbUpCCHH2L6xE9hcJUaooNNij7VeZIZpCCtUwV8F013gNltPXodZX59zlDs3T0YCCCGe2k1lzjF3vALa6DR7GwrDoeay/EoFT/6bo3/uaacRIOF14gdwXiJrkptK/P4gYWu7c3e475wnKwgB+NieoM5ZbCfAgMBAAECgYA2ctdOAeJQjbJPGrwrVBpxCxhMjkOF+uNx1OMD2ZcaGH7FTaYcigB/d0D4aMra6BzqT3NHV0ulKj+C58FwR+uo1DEaJAQ0rkvJkxJ2EB0LJDt+zb1uc8ZgUBAbAC8zMcosD09Wy2pS1HN5vj48qQBaLhDeVfpkoQ13LbIPU+SR4QJBAOTnHh8swoGUaLrXv3u+mPMBDkLiKO0UEeRCq6FkzNaFbvWQni9Gbx22cKV/KwY1/3AKxL+nveX5dYZz8BJ/HC8CQQDb8MJNNu4AHs7MxTovh4fQbioOPPMAeYlZWmjeFu3gsg2CuEsya5spcjyvxfiH1KuCJbwCv3qO9dzkQwBhlGaRAkAMkRu4Pm7XSlyNlXavxoEDJlWwGlaA3Y815ushSzVruZuj47KricRj7zYz/81O4/wIHK88jmsAizeGkCrn+Q6FAkEA2M5VCr56ED5ORsaom8+zDsB5zn1AYZhsz1rzAvKBXTzHWrOG1NYEJvAIaJDzdBotUucVlXPhQWmVbKGrj4U1MQJBAMcOJsUhk37msvslfExwT9ZmSOGgA2iJgfUXhV1u/b7C25n1g1GdoZU3efb8ow4GNfpThXqMZtEVvh3zHIM0cTs=";

    /**
     * 采用公钥进行加密
     */
    public static String rsaEncrypt(String msg) {
        RSA rsa = new RSA(PRIVATE_KEY, PUBLIC_KEY);
        return rsa.encryptBase64(msg, KeyType.PublicKey);
    }

    /**
     * 采用私钥解密
     */
    public static String rsaDecrypt(String msg) {
        RSA rsa = new RSA(PRIVATE_KEY, PUBLIC_KEY);
        return rsa.decryptStr(msg, KeyType.PrivateKey);
    }

    /**
     * 采用 sha1 算法进行单向的摘要加密
     */
    public static String digestSha1(String msg) {
        return SecureUtil.sha1(msg);
    }

    /**
     * 获取公钥
     */
    public static String getPublicKey() {
        return PUBLIC_KEY;
    }

    public static void main(String[] args) {
        RSA rsa = new RSA(PRIVATE_KEY, PUBLIC_KEY);
        String pwd = "123";
        String encryptPwd = rsa.encryptBase64(pwd, KeyType.PublicKey);
        String decryptPwd = rsa.decryptStr(encryptPwd, KeyType.PrivateKey);
        Assert.isTrue(pwd.equals(decryptPwd));
    }
}
