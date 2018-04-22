package com.zoom.risk.operating.scard.service;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.ruleconfig.common.Constants;
import com.zoom.risk.operating.ruleconfig.model.RiskNumber;
import com.zoom.risk.operating.ruleconfig.service.RiskNumberService;
import com.zoom.risk.operating.scard.dao.SCardMapper;
import com.zoom.risk.operating.scard.dao.SCardParamMapper;
import com.zoom.risk.operating.scard.dao.SCardParamRouteMapper;
import com.zoom.risk.operating.scard.dao.SCardRuleMapper;
import com.zoom.risk.operating.scard.model.SCard;
import com.zoom.risk.operating.scard.model.SCardParam;
import com.zoom.risk.operating.scard.model.SCardParamRoute;
import com.zoom.risk.operating.scard.model.SCardRule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Service("scardService")
public class SCardService {
	private static final Logger logger  = LogManager.getLogger(SCardService.class);
	@Autowired
	private SCardMapper scardMapper;
	@Autowired
	private SCardParamMapper scardParamMapper;
	@Autowired
	private SCardRuleMapper sCardRuleMapper;
	@Autowired
	private SCardParamRouteMapper sCardParamRouteMapper;
	@Autowired
	private RiskNumberService riskNumberService;

	public List<SCard> selectBySceneNo(String sceneNo){
		try {
			return scardMapper.selectBySceneNo(sceneNo);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public List<SCard> selectBySceneNoList(List<String> sceneNoList){
		try {
			return scardMapper.selectBySceneNoList(sceneNoList);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public int selectCountBySceneNo(String sceneNo) throws Exception{
		return scardMapper.selectCountBySceneNo(sceneNo);
	}
	
	public SCard selectById(long policyId){
		try {
			return scardMapper.selectByPrimaryKey(policyId);
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
		scardMapper.updateStatus(map);
        //更新规则状态

	}
	
	public ResultCode delById(long PolicyId){
		try {
			scardMapper.deleteByPrimaryKey(PolicyId);
		} catch (Exception e) {
			logger.error(e);
			return ResultCode.DB_ERROR; 
		}
		return ResultCode.SUCCESS;
	}
	
	public boolean update(SCard Policy){
		try {
			scardMapper.updateByPrimaryKey(Policy);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	
	@Transactional(rollbackFor=Exception.class)
	public boolean insert(SCard policy) throws Exception{
		policy.setScardNo(riskNumberService.getJYLRiskNumber(RiskNumber.SCARD_CLASS, policy.getSceneNo()));
		policy.setStatus(Constants.STATUS_DRAFT);
		this.scardMapper.insert(policy);
		return true;
	}
	
	public boolean exists(String sceneNo, int weightValue, Long policyId, String name){
		Map<String, Object> map = new HashMap<>();
		map.put("sceneNo", sceneNo);
		map.put("policyName", name);
		map.put("weightValue", weightValue);
		map.put("policyId", policyId==null?0:policyId.longValue());
		return scardMapper.exists(map)>0?true:false;
	}

	public SCard selectByPrimaryKey(Long id){
		return  scardMapper.selectByPrimaryKey(id);
	}


	@Transactional
	public void cloneScard(Long scardId){
		SCard sCard = scardMapper.selectByPrimaryKey(scardId);
		sCard.setId(null);
		sCard.setName(sCard.getName()+"-clone");
		sCard.setScardNo(riskNumberService.getJYLRiskNumber(RiskNumber.SCARD_CLASS, sCard.getSceneNo()));
		sCard.setWeightValue(sCard.getWeightValue()+1);
		sCard.setStatus(1);     //评分卡状态 1:拟草 2:生效 3: 废弃
		scardMapper.insert(sCard);
		List<SCardParam> sCardParams = scardParamMapper.getSCardParams(scardId);
		for(SCardParam sCardParam : sCardParams){
			Long scardParamId = sCardParam.getId();
			sCardParam.setId(null);
			sCardParam.setScardId(sCard.getId());
			sCardParam.setParamNo(riskNumberService.getJYLRiskNumber(RiskNumber.SCARD_CLASS_PARAM, sCard.getSceneNo()));
			scardParamMapper.insertParams(sCardParam);
			List<SCardParamRoute> sCardParamRoutes = sCardParamRouteMapper.getRoutesByParam(scardParamId);
			for(SCardParamRoute sCardParamRoute : sCardParamRoutes ){
				sCardParamRoute.setId(null);
				sCardParamRoute.setParamId(sCardParam.getId());
				sCardParamRouteMapper.saveParamRoute(sCardParamRoute);
			}
		}
		List<SCardRule> sCardRules = sCardRuleMapper.getRulesBySCard(scardId);
		for(SCardRule sCardRule : sCardRules ){
			sCardRule.setId(null);
			sCardRule.setScardId(sCard.getId());
			sCardRuleMapper.saveScardRule(sCardRule);
		}

	}

}
