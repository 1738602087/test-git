package cn.repeatlink.module.judicial.util;

import cn.hutool.core.codec.Base64;
import cn.repeatlink.framework.util.SysConfigUtil;

/**
 * 数据双重加密（AES+RSA）
 *
 * @author Lsq
 * @date 2020-12-11 9:41
 */
public class DoubleEncrypt {

    //RSA的公钥、私钥
    private String publicKey;
    private String privateKey;
    //AES加密后的密文
    private byte[] resultAES;

    private static DoubleEncrypt doubleEncrypt = new DoubleEncrypt();

    public static DoubleEncrypt initialize() {
        return doubleEncrypt;
    }

    //初始化公钥、私钥
    private void initializeKeys() {
        publicKey = SysConfigUtil.instance().getValue("image.Encrypt.publicKey");
        privateKey = SysConfigUtil.instance().getValue("image.Encrypt.privateKey");
    }

    /**
     * 对数据进行AES对称加密
     *
     * @param date
     * @return AES密钥
     */
    private byte[] encryptAES(String date) {
        //AES随机密钥
        byte[] randomKey = AESUtil.randomKey(256);
        byte[] result = date.getBytes();
        //AES加密->AES密文
        resultAES = new AESUtil(randomKey).encrypt(result);
        //返回随机AES密钥
        return randomKey;
    }

    /**
     * 对AES的密钥进行RSA非对称加密
     *
     * @param imgbase64data
     * @return RSA密文（进行加密后的AES密钥）
     */
    private byte[] encryptKeyRSA(String imgbase64data) {
        byte[] keyAES = encryptAES(imgbase64data);
        //对AES密钥进行RSA非对称加密
        return new RSAUtil(publicKey).encrypt(keyAES);
    }


    /**
     * 对外加密方法
     *
     * @param date
     * @return RSA密文+AES密文
     */
    public String encrypt(String date) {
        //初始化公（私）密钥
        initializeKeys();
        //RSA密文
        byte[] resultRSA = encryptKeyRSA(date);
        String secretRSA = Base64.encode(resultRSA);
        //AES密文
        String secretAES = Base64.encode(resultAES);
        return secretRSA + "AES:" + secretAES;
    }

    /**
     * 对外解密方法
     *
     * @param secret
     * @return
     */
    public String decrypt(String secret) {
        String[] split = secret.split("AES:");
        //对RSA密文进行私钥解密（获取AES密钥）
        byte[] keyAES = new RSAUtil(publicKey, privateKey).decrypt(Base64.decode(split[0]));
        //AES密钥对密文内容解密（获取AES数据）
        byte[] result = new AESUtil(keyAES).decrypt(Base64.decode(split[1]));
        return new String(result);
    }
}
