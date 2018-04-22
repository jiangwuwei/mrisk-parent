/**
 * 
 */
package com.zoom.risk.platform.datacenter.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin
 *Nov 26, 2015
 */
public interface QuotaJadeService {
	
	public Object getObjectByInput(String sql);
	
	public String getStringByInput(String sql);
	
	public BigDecimal getDecimalByInput(String sql);
	
	public Map<String,Object> getMapByInput(String sql);
	
	public List<Object> getListByInput(String sql);
	
}
