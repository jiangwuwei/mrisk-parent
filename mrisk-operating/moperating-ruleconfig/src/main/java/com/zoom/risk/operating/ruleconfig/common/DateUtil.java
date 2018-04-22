package com.zoom.risk.operating.ruleconfig.common;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Calendar;

/**
 * Created by liyi8 on 2017/3/13.
 */
public class DateUtil {

    public static String getCurrDayStart(){
        return getDayStart(Calendar.getInstance());
    }

    public static String getCurrDayEnd(){
        return getDayEnd(Calendar.getInstance());
    }

    public static String getDayStart(Calendar calendar){
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
        return DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getDayEnd(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return DateFormatUtils.format(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
    }
}
