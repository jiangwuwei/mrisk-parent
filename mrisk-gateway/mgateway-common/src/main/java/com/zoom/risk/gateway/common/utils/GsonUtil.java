package com.zoom.risk.gateway.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author jiangyulin
 *Sep 23, 2015
 */
public class GsonUtil {
	public static final String FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static Gson getGson(){
		return new GsonBuilder().serializeNulls().setDateFormat(FORMAT).create();
	}
	
	public static Gson getNoNullGson(){
		return new GsonBuilder().setDateFormat(FORMAT).create();
	}
}
