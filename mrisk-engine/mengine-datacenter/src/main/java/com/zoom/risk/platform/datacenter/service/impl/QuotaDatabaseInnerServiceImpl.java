/**
 * 
 */
package com.zoom.risk.platform.datacenter.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zoom.risk.platform.datacenter.dao.QuotaDatabaseMapper;
import com.zoom.risk.platform.datacenter.service.QuotaDatabaseInnerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jiangyulin
 *Nov 19, 2015
 */
@Service("quotaDatabaseInnerService")
public class QuotaDatabaseInnerServiceImpl implements QuotaDatabaseInnerService {
	
	@Resource(name="quotaDatabaseMapper")
	private QuotaDatabaseMapper quotaDatabaseMapper;
	
	@Transactional(readOnly=true)
	public Object getObjectByInput(String sql){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sql);
		return quotaDatabaseMapper.getObjectByInput(map);
	}
	
	@Transactional(readOnly=true)
	public String getStringByInput(String sql){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sql);
		return quotaDatabaseMapper.getStringByInput(map);
	}
	
	@Transactional(readOnly=true)
	public BigDecimal getDecimalByInput(String sql){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sql);
		return quotaDatabaseMapper.getDecimalByInput(map);
	}

	@Transactional(readOnly=true)
	public Map<String,Object> getMapByInput(String sql){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sql);
		return quotaDatabaseMapper.getMapByInput(map);
	}

	@Transactional(readOnly=true)
	public List<Map<String,Object>> getListByInput(String sql){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sql);
		return quotaDatabaseMapper.getListByInput(map);
	}

}
