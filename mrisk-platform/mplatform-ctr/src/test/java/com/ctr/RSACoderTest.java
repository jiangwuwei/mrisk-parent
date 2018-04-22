package com.ctr;

import com.zoom.risk.platform.ctr.util.RSACoder;

public class RSACoderTest {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
          String macAddress = "00:1C:42:CB:EC:D6";  //李涛
//        //String[] keyMap = RSACoderTest.generateKeys();
//        String publicStr = RSACoder.PUBLIC_LEJR_KEY;
//        String privateStr =   KeyPairs.PRIVATE_KEY;
//        System.out.println("公钥:" + publicStr );
//        System.out.println("私钥:" + privateStr );
//        System.out.println("================密钥对构造完毕,甲方将公钥公布给乙方，开始进行加密数据的传输=============");
//        String str = "1520574127490-343333333333333333333333";
//        System.out.println("===========甲方向乙方发送加密数据==============");
//        System.out.println("原文:\r\n" + str);
//        //甲方进行数据的加密
//        String code1 = RSACoder.encryptByPrivateKey(str, privateStr);
//        System.out.println("加密后的数据：" + code1 );
//        System.out.println("===========乙方使用甲方提供的公钥对数据进行解密==============");
//        //乙方进行数据的解密
//        String decode1 = RSACoder.decryptByPublicKey(code1, publicStr);
//        System.out.println("乙方解密后的数据:\r\n" + new String(Base64.getDecoder().decode(decode1),"UTF-8") + "");
//        System.out.println(System.currentTimeMillis());

        String licenseDate = "2019-01-01";
        String macCheck = "1";   // 0 不校验  其他的校验
        long time = java.sql.Date.valueOf(licenseDate).getTime();
        String tem = time + "-" + macCheck + "-" + macAddress;
        String lices = RSACoder.encryptByPrivateKey(tem, KeyPairs.PRIVATE_KEY);
        System.out.println("加密后的数据\r\n" + lices );

    }
}
