package com.zoom.risk.platform.ctr.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Base64;
import java.util.Random;

public class LsManager {
    private static final Logger logger = LogManager.getLogger("com.google.LicenseManager");
    private static LsManager instance;
    private String authMacAddress;
    private long authDate;
    private boolean isMacCheck;
    private String content;
    private String machineMac;

    static{
        instance = new LsManager();
        init();
    }

    private static void init(){
        try {
            String temContent = RSACoder.decryptByPublicKey(instance.content,RSACoder.PUBLIC_LEJR_KEY);
            instance.content = new String(Base64.getDecoder().decode(temContent),"UTF-8");
            instance.authDate = Long.parseLong(instance.content.substring(0,instance.content.indexOf("-")));
            String left = instance.content.substring(instance.content.indexOf("-")+1);
            instance.isMacCheck = Integer.parseInt(left.substring(0,left.indexOf("-"))) == 0 ? false : true;
            instance.authMacAddress = left.substring(left.indexOf("-")+1);
        } catch (Exception e) {
            logger.error("",e);
        }
    }


    private LsManager(){
        content = FileLoader.loadFileContent();
    }

    public void check(){
        long randomDays = new Random().nextInt(10) *24*3600*1000 ;
        long currentDate = System.currentTimeMillis();
        if ( this.machineMac == null ) {
            this.machineMac = MacTools.getMacAddress();
        }
        if ( currentDate - randomDays > this.authDate ){
            String errorMessage = "System ERROR\r\n*****\r\n*****\r\n*****\r\n*****授权License已过期或者机器无效，请联系商家:13521962864 \r\n*****\r\n*****\r\n*****\r\n"; //, 到期日期:" + new java.sql.Date(authDate)+"\r\n*****\r\n*****\r\n*****\r\n";
            logger.error(errorMessage);
            System.out.println(errorMessage);
            throw new RuntimeException(errorMessage);
        }
        if ( this.isMacCheck && ( !this.authMacAddress.equals(machineMac) )){
            String errorMessage = "System ERROR\n*****\r\n*****\n*****\n*****授权License已过期或者机器无效，请联系商家:13521962864 \r\n*****\r\n*****\r\n*****\r\n";  //, 授权机器machineMac:" + authMacAddress+"\r\n*****\r\n*****\r\n*****\r\n";
            logger.error(errorMessage);
            System.out.println(errorMessage);
            throw new RuntimeException(errorMessage);
        }

    }

    public static LsManager getInstance() {
        return instance;
    }

    public synchronized String getMac() {
        if ( this.machineMac == null ) {
            this.machineMac = MacTools.getMacAddress();
        }
        return machineMac;
    }

}
