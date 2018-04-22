package com.zoom.risk.jade.proxy.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zoom.risk.jade.proxy.CommonQuerySvc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zoom.risk.jade.dao.CommonQueryMapper;


@Service("commonQuerySvc")
public class CommonQueryServiceImpl implements CommonQuerySvc {
	
	@Resource(name="commonQueryMapper")
	private CommonQueryMapper commonQueryMapper;
	
	@Override
	@Transactional(transactionManager="transactionManager", readOnly=true)
	public String getDataStringByInput(String sql){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sql);
		return commonQueryMapper.getDataStringByInput(map);
	}
	
	@Override
	@Transactional(transactionManager="transactionManager", readOnly=true)
	public Object getObjectByInput(String sql){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sql);
		return commonQueryMapper.getObjectByInput(map);
	}
	
	@Override
	@Transactional(transactionManager="transactionManager", readOnly=true)
	public BigDecimal getDataDecimalByInput(String sql){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sql);
		return commonQueryMapper.getDataDecimalByInput(map);
	}
	
	@Override
	@Transactional(transactionManager="readonlyTransactionManager", readOnly=true)
	public Map<String,Object> getDataMapByInput(String sql){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sql);
		return commonQueryMapper.getDataMapByInput(map);
	}
	
	@Override
	@Transactional(transactionManager="transactionManager", readOnly=true)
	public List<Map<String,Object>> getDataListByInput(String sql){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sql);
		return commonQueryMapper.getDataListByInput(map);
	}
}
