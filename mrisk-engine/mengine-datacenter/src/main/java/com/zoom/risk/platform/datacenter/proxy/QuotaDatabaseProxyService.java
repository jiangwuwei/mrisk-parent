/**
 * 
 */
package com.zoom.risk.platform.datacenter.proxy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin
 *Nov 26, 2015
 */
public interface QuotaDatabaseProxyService {
	
	public Object getObjectByInput(String sql,String dsKey);
	
	public String getStringByInput(String sql,String dsKey);
	
	public BigDecimal getDecimalByInput(String sql,String dsKey);
	
	public Map<String,Object> getMapByInput(String sql,String dsKey);
	
	public List<Object> getListByInput(String sql,String dsKey);
}
