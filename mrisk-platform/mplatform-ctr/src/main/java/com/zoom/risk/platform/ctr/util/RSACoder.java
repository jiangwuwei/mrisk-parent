package com.zoom.risk.platform.ctr.util;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

public class RSACoder {
    public static final String PUBLIC_LEJR_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn4KO905Zlw8JsS44ZCOUQ8htuBFoKcszwZsh/6UTO8TmD7NzpUqUKSVgFfbiRQNID0bWhrl1EvaAhAbop+AaJGTPuKn22+UF2cwYRmRXhNRNT3kO+xWUwgdSv4JZj29qhK6bbnCD2IarhHJ6tDjugskb3NRw5nIOyzNt/oy1coStdJTZtS5NdGu8UOzbx/e6InA9xSTPAxCKTBEpTYilW93wOHcPlWMy01g9+fQDWJmW/Mqajw2otW3OHhvt6pdF0YILR2KuM0f5Fr+oJXqDoqi1pzYau4uVXUDNj02mE5iJs+bEGOZ8DUUCnn3tQSsHjShuPTz71OjP9fMy0o+GWwIDAQAB";
    public static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;

    public static String[] generateKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        String pubKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String priKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        return new String[]{pubKey,priKey};
    }


    public static String encryptByPrivateKey(String data, String privateKeyStr) throws Exception {
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyStr));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(data.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(result);
    }

    public static String decryptByPublicKey(String data, String publicStr) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicStr));
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        byte[] result = cipher.doFinal(Base64.getDecoder().decode(data));
        return Base64.getEncoder().encodeToString(result);
    }
}
