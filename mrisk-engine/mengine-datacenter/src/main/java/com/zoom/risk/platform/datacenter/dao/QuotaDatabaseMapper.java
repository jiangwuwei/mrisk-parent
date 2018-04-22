/**
 * 
 */
package com.zoom.risk.platform.datacenter.dao;

import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * @author jiangyulin
 *Nov 21, 2015
 */
@ZoomiBatisRepository(value="quotaDatabaseMapper")
public interface QuotaDatabaseMapper {

	public Object getObjectByInput(Map<String,Object> map);
	
	public String getStringByInput(Map<String,Object> map);
	
	public BigDecimal getDecimalByInput(Map<String,Object> map);
	
	public Map<String,Object> getMapByInput(Map<String,Object> map);
	
	public List<Map<String,Object>> getListByInput(Map<String,Object> map);
}
