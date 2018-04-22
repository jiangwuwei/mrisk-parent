package com.zoom.risk.jade.api;

import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */
public interface JadeDataApi {
	
	public int insertEvent(Map<String, Object> parameterMap);
	
	public int updateEvent(Map<String, Object> parameterMap);
	
	public Map<String, Object> convert2DatabaseType(Map<String, Object> riskInput);
	
	public <R> R queryDataBySQL(String sql, Class<R> c);
}
