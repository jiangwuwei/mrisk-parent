/**
 * 
 */
package com.zoom.risk.jade.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.zoom.risk.jade.dao.RiskEventMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zoom.risk.jade.service.RiskEventService;

/**
 * @author jiangyulin
 *Oct 19, 2015
 */
@Service("riskEventService")
public class RiskEventServiceImpl implements RiskEventService {
 
	@Resource(name="riskEventMapper")
	private RiskEventMapper riskEventMapper;
	
	@Override
	@Transactional
	public void singleInsertMap(Map<String, Object> map) {
		riskEventMapper.singleInsertMap(map);
	}
 
	@Override
	@Transactional
	public void singleUpdateMap(Map<String, Object> map) {
		riskEventMapper.singleUpdateMap(map);
	}

}
