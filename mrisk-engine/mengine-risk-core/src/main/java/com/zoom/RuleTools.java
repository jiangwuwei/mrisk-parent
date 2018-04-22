package com.zoom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiangyulin on 2015/12/6.
 */
public class RuleTools {
    private static final Logger logger = LogManager.getLogger(RuleTools.class);
    public static final String RULE_GETTIME = "RuleTools.getTime";
    public static final String RULE_ADDTIME = "RuleTools.addTime";
    public static final String RULE_ISEMPTY = "RuleTools.isEmpty";

    /**
     * @param riskDate
     * @return
     */
    public static final Integer getTime(String riskDate){
        Integer result = 0;
        try {
            result = Integer.parseInt(riskDate.substring(11, 13));
        }catch(Exception e){
            logger.error("RuleTools.getTime happens error",e);
        }
        return result;
    }

    /**
     * @param someInput
     * @return
     */
    public static final boolean isEmpty(Object someInput){
        boolean result = false;
        try {
             if ( someInput == null || "".equals(String.valueOf(someInput).trim()) || "null".equals(String.valueOf(someInput).trim())){
                 result = true;
             }
        }catch(Exception e){
            logger.error("RuleTools.isEmpty happens error",e);
        }
        return result;
    }

    /**
     * @param riskDate
     * @param value
     * @param type   d:day,  h:hour, m:minute
     * @return
     */
    public static final String addTime(String riskDate, int value, String type){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try{
            date = format.parse(riskDate);
        }catch(Exception e){
            logger.error("RuleTools.addTime happens error",e);
        }
        long time = 0;
        if ( "d".equals(type.trim())){
            time = value * 24 * 3600 * 1000;
        }else if ( "h".equals(type.trim())){
            time = value * 60 * 60 * 1000;
        }else if ( "m".equals(type.trim())){
            time = value * 60 * 1000;
        }else{
            time =  value * 1000;
        }
        date.setTime(date.getTime() + time);
        return format.format(date);
    }
}
