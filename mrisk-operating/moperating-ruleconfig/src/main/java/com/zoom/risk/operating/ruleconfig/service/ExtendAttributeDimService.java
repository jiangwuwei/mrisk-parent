package com.zoom.risk.operating.ruleconfig.service;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.ruleconfig.dao.ExtendAttributeDimMapper;
import com.zoom.risk.operating.ruleconfig.model.ExtendAttributeDim;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("extendAttributeDimService")
public class ExtendAttributeDimService {
	
	private static final Logger logger  = LogManager.getLogger(ExtendAttributeDimService.class);
	
	@Autowired
	private ExtendAttributeDimMapper extendAttributeDimMapper;
	
	public List<ExtendAttributeDim> selectAll(){
		try {
			return extendAttributeDimMapper.selectAll();
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
}
