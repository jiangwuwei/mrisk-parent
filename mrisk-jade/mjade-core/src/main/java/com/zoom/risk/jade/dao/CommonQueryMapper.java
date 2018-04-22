package com.zoom.risk.jade.dao;

import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * Lexusis通用查询DAO
 * @author jiangyulin
 *
 */
@ZoomiBatisRepository(value="commonQueryMapper")
public interface CommonQueryMapper{

	public Object getObjectByInput(Map<String,Object> map);
	
	public String getDataStringByInput(Map<String,Object> map);
	
	public BigDecimal getDataDecimalByInput(Map<String,Object> map);
	
	public Map<String,Object> getDataMapByInput(Map<String,Object> map);
	
	public List<Map<String,Object>> getDataListByInput(Map<String,Object> map);
}
