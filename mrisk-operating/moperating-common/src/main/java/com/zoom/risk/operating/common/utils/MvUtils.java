package com.zoom.risk.operating.common.utils;

import com.zoom.risk.operating.common.po.ResultCode;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
public class MvUtils {
	private static final String BASE_DIR = "/src/page";

	public static ModelAndView getView(String name) {
		ModelAndView mView = new ModelAndView();
		mView.setViewName( BASE_DIR+name);
		return mView;
	}
	
	/**
	 * 
	 * @param ret
	 * @param dataArr the param must be key value pair, for example: id:1
	 * @return
	 */
	public static Map<String, Object> formatJsonResult(boolean ret, String... dataArr){
		Map<String, Object> map = new HashMap<>();
		if(ret){
			map.put("res", ResultCode.SUCCESS);
			spiltParams(map, dataArr);
		}else{
			map.put("res", ResultCode.FAILED);
		}
		return map;
	}
	
	/**
	 * 
	 * @param resultCode
	 * @param dataArr the param must be key value pair, for example: id:1
	 * @return
	 */
	public static Map<String, Object> formatJsonResult(ResultCode resultCode, String... dataArr){
		Map<String, Object> map = new HashMap<>();
		map.put("res", resultCode);
		spiltParams(map, dataArr);
		return map;
	}

	private static void spiltParams(Map<String,Object> map, String[] dataArr){
		for (String string : dataArr) {
			String[] paras = string.split(":");
			map.put(paras[0], paras[1]);
		}
	}
}
