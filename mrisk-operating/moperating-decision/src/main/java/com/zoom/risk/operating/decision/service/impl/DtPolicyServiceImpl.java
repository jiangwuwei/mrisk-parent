package com.zoom.risk.operating.decision.service.impl;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.decision.common.Constants;
import com.zoom.risk.operating.decision.dao.DtPolicyMapper;
import com.zoom.risk.operating.decision.po.TPolicy;
import com.zoom.risk.operating.decision.service.DtPolicyService;
import com.zoom.risk.operating.decision.service.RiskDecisionTreeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("dtPolicyService")
public class DtPolicyServiceImpl implements DtPolicyService {
	@Autowired
	private DtPolicyMapper dtPolicyMapper;
	@Autowired
	private RiskDecisionTreeService riskDecisionTreeService;
	
	private static final Logger logger  = LogManager.getLogger(DtPolicyServiceImpl.class);
	
	public List<TPolicy> selectBySceneNo(String sceneNo){
		try {
			return dtPolicyMapper.selectBySceneNo(sceneNo);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public List<TPolicy> selectBySceneNoList(List<String> sceneNoList){
		try {
			return dtPolicyMapper.selectBySceneNoList(sceneNoList);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public int selectCountBySceneNo(String sceneNo) throws Exception{
		return dtPolicyMapper.selectCountBySceneNo(sceneNo);
	}
	
	public TPolicy selectById(long policyId){
		try {
			return dtPolicyMapper.selectByPrimaryKey(policyId);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public void updateStatus(long policyId, int status) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put("policyId", policyId);
		map.put("status", status);
		dtPolicyMapper.updateStatus(map);
	}
	
	public ResultCode delById(long PolicyId){
		try {
			dtPolicyMapper.deleteByPrimaryKey(PolicyId);
		} catch (Exception e) {
			logger.error(e);
			return ResultCode.DB_ERROR; 
		}
		return ResultCode.SUCCESS;
	}
	
	public boolean update(TPolicy tPolicy){
		try {
			dtPolicyMapper.updateByPrimaryKey(tPolicy);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	
	@Transactional(rollbackFor=Exception.class)
	public boolean insert(TPolicy tPolicy) throws Exception{
		tPolicy.setPolicyNo(riskDecisionTreeService.getNextNumber(Constants.ENTITY_CLASS_POLICY, tPolicy.getSceneNo()));
		tPolicy.setStatus(Constants.STATUS_DRAFT);
		dtPolicyMapper.insert(tPolicy);
		return true;
	}

	public boolean exists(String sceneNo, int weightValue, Long policyId, String name){
		Map<String, Object> map = new HashMap<>();
		map.put("sceneNo", sceneNo);
		map.put("policyName", name);
		map.put("weightValue", weightValue);
		map.put("policyId", policyId==null?0:policyId.longValue());
		return dtPolicyMapper.exists(map)>0?true:false;
	}

}
