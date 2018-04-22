package com.zoom.risk.operating.scard.service;

import com.zoom.risk.operating.scard.dao.SCardRuleMapper;
import com.zoom.risk.operating.scard.model.SCardRule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Service("scardRuleService")
public class SCardRuleService {
	private static final Logger logger  = LogManager.getLogger(SCardRuleService.class);

	@Autowired
	private SCardRuleMapper scardRuleMapper;

	public List<SCardRule> getRulesBySCard(Long scardId){
 		return  scardRuleMapper.getRulesBySCard(scardId);
	}

	public void deleteByPrimaryKey(Long paramId){
		scardRuleMapper.deleteByPrimaryKey(paramId);
	}

	public Long saveScardRule(SCardRule route){
		return scardRuleMapper.saveScardRule(route);
	}

	public void updateByPrimaryKey(SCardRule route){
		scardRuleMapper.updateByPrimaryKey(route);
	}
}
