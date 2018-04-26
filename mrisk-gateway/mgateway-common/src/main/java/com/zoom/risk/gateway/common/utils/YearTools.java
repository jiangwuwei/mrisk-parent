package com.zoom.risk.gateway.common.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;

public class YearTools {
    private static final Logger logger = LogManager.getLogger(YearTools.class);

    public static int getRealYearByIdCardNumber(String idCardNumber) {
        int age = 0;
        if ( idCardNumber == null || idCardNumber.trim().length() < 15 ||  idCardNumber.trim().length() > 18){
            return age;
        }
        try {
            String cardYear = "";
            String cardMonth = "";
            if (idCardNumber.length() == 18) {
                cardYear = idCardNumber.substring(6, 10);// 得到年份
                cardMonth = idCardNumber.substring(10, 12);// 得到月份
            } else {
                cardYear = "19" + idCardNumber.substring(6, 8);// 年份
                cardMonth = idCardNumber.substring(8, 10);// 月份
            }
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH)+1;
            if (Integer.parseInt(cardMonth) < currentMonth) {
                age = 1;
            }
            age = currentYear - Integer.parseInt(cardYear) + age;
        }catch (Exception e){
            logger.error("",e);
        }
        return age;
    }
}
