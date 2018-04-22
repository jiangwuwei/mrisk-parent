package com.zoom.risk.jade.proxy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface CommonQuerySvc {

	public String getDataStringByInput(String sql);

	public BigDecimal getDataDecimalByInput(String sql);

	public Map<String,Object> getDataMapByInput(String sql);

	public List<Map<String,Object>> getDataListByInput(String sql);
	
	public Object getObjectByInput(String sql);
}