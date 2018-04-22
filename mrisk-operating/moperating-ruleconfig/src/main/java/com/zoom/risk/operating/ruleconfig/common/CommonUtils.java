package com.zoom.risk.operating.ruleconfig.common;

import com.google.common.collect.Maps;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommonUtils {
	
	private static Map<String, String> certiMap = Maps.newHashMap();
	
	static{
		List<String> certiNameList = Arrays.asList(Constants.CERTIFICATE_CH_ARRAY);
		List<String> certiCodeList = Arrays.asList(Constants.CERTIFICATE_ENG_ARRAY);
		for (int i = 0; i < Constants.CERTIFICATE_CH_ARRAY.length; i++) {
			certiMap.put(certiCodeList.get(i),certiNameList.get(i));
		}
	}
	
	/**
	 * 根据证件代码获取证件名字
	 * @param certiCode 证件代 码
	 */
	public static String getCertiName(String certiCode){
		return certiMap.get(certiCode);
	}
}
