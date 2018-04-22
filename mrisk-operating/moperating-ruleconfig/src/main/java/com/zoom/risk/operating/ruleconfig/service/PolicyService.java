package com.zoom.risk.operating.ruleconfig.service;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.ruleconfig.common.Constants;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.ruleconfig.dao.PolicyMapper;
import com.zoom.risk.operating.ruleconfig.model.Policy;
import com.zoom.risk.operating.ruleconfig.model.RiskNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("policyService")
public class PolicyService {
	@Autowired
	private PolicyMapper policyMapper;
	@Autowired
	private RuleService ruleService;
	@Autowired
	private RiskNumberService riskNumberService;
	
	private static final Logger logger  = LogManager.getLogger(PolicyService.class);
	
	public List<Policy> selectBySceneNo(String sceneNo){
		try {
			return policyMapper.selectBySceneNo(sceneNo);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public List<Policy> selectBySceneNoList(List<String> sceneNoList){
		try {
			return policyMapper.selectBySceneNoList(sceneNoList);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public int selectCountBySceneNo(String sceneNo) throws Exception{
		return policyMapper.selectCountBySceneNo(sceneNo);
	}
	
	public Policy selectById(long policyId){
		try {
			return policyMapper.selectByPrimaryKey(policyId);
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
		policyMapper.updateStatus(map);
        //更新规则状态
        ruleService.updateStatus(policyId, status, 2);
	}
	
	public ResultCode delById(long PolicyId){
		try {
			policyMapper.deleteByPrimaryKey(PolicyId);
		} catch (Exception e) {
			logger.error(e);
			return ResultCode.DB_ERROR; 
		}
		return ResultCode.SUCCESS;
	}
	
	public boolean update(Policy Policy){
		try {
			policyMapper.updateByPrimaryKey(Policy);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	
	@Transactional(rollbackFor=Exception.class)
	public boolean insert(Policy policy) throws Exception{
		int seqNo = riskNumberService.selectSeqNo(RiskNumber.ENTITY_CLASS_POLICY, policy.getSceneNo());
		//get new quota number
		policy.setPolicyNo(riskNumberService.getRiskNumber(seqNo+1, policy.getSceneNo(),RiskNumber.ENTITY_CLASS_POLICY));
		policy.setStatus(Constants.STATUS_DRAFT);
		//insert or update quota seq no
		riskNumberService.insertOrUpdate(RiskNumber.ENTITY_CLASS_POLICY, policy.getSceneNo(), seqNo+1);
		policyMapper.insert(policy);
		return true;
	}
	
	public boolean exists(String sceneNo, int weightValue, Long policyId, String name){
		Map<String, Object> map = new HashMap<>();
		map.put("sceneNo", sceneNo);
		map.put("policyName", name);
		map.put("weightValue", weightValue);
		map.put("policyId", policyId==null?0:policyId.longValue());
		return policyMapper.exists(map)>0?true:false;
	}
}
