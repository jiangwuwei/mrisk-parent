package com.zoom.risk.platform.kafka.consumer.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author jiangyulin
 *Oct 31, 2015
 */
public class Utils {
	private static final Logger logger = LogManager.getLogger(Utils.class);
    private static final Pattern pattern = Pattern.compile("[0-9]*");

    public static String getCurrentIndex(int subMonth){
		Calendar ca = Calendar.getInstance(Locale.CHINA);
		int year = ca.get(Calendar.YEAR);
		int month = ca.get(Calendar.MONTH) + 1 - subMonth;
		if ( month == 0 ){
			year = year -1;
			month = 12;
		}
		return  "" + year + ( month >  9 ? month : ("0" + month) );
	}


	/**
	 * empty check
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		boolean result = false;
		if ( obj instanceof String ){
			String v = (String) obj ;
			if ( v == null || "".equals(v.trim()) || "null".equals(v.trim()) ) {
				result = true;
			}
		}else{
			String v = String.valueOf(obj);
			if ( "".equals(v.trim()) || "null".equals(v.trim()) ) {
				result = true;
			}
		}
		return result;
	}

    /**
     * convert ip v4  to a long value
     * @param ip
     * @return
     */
	public static Long ip2Long(String ip) {
		Long num = null;
		try {
			ip = ip.replaceAll("[^0-9\\.]", "");
			String[] ips = ip.split("\\.");
			if (ips.length == 4) {
				num = Long.parseLong(ips[0], 10) * 256L * 256L * 256L + Long.parseLong(ips[1], 10) * 256L * 256L + Long.parseLong(ips[2], 10) * 256L + Long.parseLong(ips[3], 10);
				num = num >>> 0;
			}
		} catch (Exception ex) {
			logger.error("", ex);
		}
		return num;
	}

	public static boolean isNumber(String code){
	    boolean result = true;
        Matcher isNum = pattern.matcher(code);
        if( !isNum.matches() ){
            result = false;
        }
        return result;
    }

    public static boolean isEmpty(String str){
        boolean result = false;
        if ( str == null || "".equals(str) || "null".equals(str)){
            result = true;
        }
        return result;
    }

	public static void main(String args[]){
		System.out.println(getCurrentIndex(0));
		System.out.println(getCurrentIndex(1));
	}
}
