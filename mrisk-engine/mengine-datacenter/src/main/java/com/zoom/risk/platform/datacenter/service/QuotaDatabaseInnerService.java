/**
 * 
 */
package com.zoom.risk.platform.datacenter.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin
 *Nov 19, 2015
 */
public interface QuotaDatabaseInnerService {
	
	public Object getObjectByInput(String sql);
	
	public String getStringByInput(String sql);
	
	public BigDecimal getDecimalByInput(String sql);
	
	public Map<String,Object> getMapByInput(String sql);
	
	public List<Map<String,Object>> getListByInput(String sql);
}
