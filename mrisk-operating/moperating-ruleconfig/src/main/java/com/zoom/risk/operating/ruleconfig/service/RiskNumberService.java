package com.zoom.risk.operating.ruleconfig.service;

import com.zoom.risk.operating.ruleconfig.dao.RiskNumberMapper;
import com.zoom.risk.operating.ruleconfig.model.RiskNumber;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("quotaNumberService")
public class RiskNumberService {
	private static final Logger logger = LogManager.getLogger(RiskNumberService.class);
	
	private static final int NUMBER_LEN = 7;
	
	@Autowired
	private RiskNumberMapper riskNumberMapper;
	
	public void insertOrUpdate(String entityClass, String sceneNo, int seqNo){
		RiskNumber quotaNumber = new RiskNumber();
		quotaNumber.setEntityClass(entityClass);
		quotaNumber.setSceneNo(sceneNo);
		quotaNumber.setSeqNo(seqNo);
		logger.info("insertOrUpdate quotaNumber "+quotaNumber.toString());
		if(quotaNumber.getSeqNo() == 1){
			riskNumberMapper.insert(quotaNumber);
		}else{
			riskNumberMapper.update(quotaNumber);
		}
	}
	
	public int selectSeqNo(String entityClass, String sceneNo){
		RiskNumber quotaNumber = new RiskNumber();
		quotaNumber.setEntityClass(entityClass);
		quotaNumber.setSceneNo(sceneNo);
		Integer seqNo = riskNumberMapper.selectSeqNo(quotaNumber);
		return seqNo==null?0:seqNo.intValue();
	}
	
	public String getRiskNumber(int seqNo, String sceneNo, String entityClass){
		String numPart = seqNo+"";
		if(NUMBER_LEN- numPart.length()>0){
			numPart = StringUtils.leftPad(numPart, NUMBER_LEN, "0");
		}
		String format = entityClass+ "%s_%s";
		return String.format(format, sceneNo, numPart);
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public String getJYLRiskNumber(String entityClass, String sceneNo){
		int seqNo =  selectSeqNo(entityClass, sceneNo);
		String number = getRiskNumber(seqNo+1, sceneNo,entityClass);
		insertOrUpdate(entityClass, sceneNo, seqNo+1);
		return number;
	}
}
