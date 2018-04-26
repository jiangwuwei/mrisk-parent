/**
 * 
 */
package com.zoom.risk.gateway.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author jiangyulin Oct 26, 2015
 */
public class Utils {

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

	public static  boolean isNotEmpty(String obj){
		boolean result = true;
		if ( obj == null || "".equals(obj.trim()) || "null".equals(obj.trim()) ) {
			result = false;
		}
		return result;
	}
	/**
	 * get current date time string
	 * 
	 * @return
	 */
	public static String getCurrentYYYYMMDDHHMMSS() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(System.currentTimeMillis());
	}

	public static long getCurrentTimeMillis(){
		return System.currentTimeMillis();
	}
}
