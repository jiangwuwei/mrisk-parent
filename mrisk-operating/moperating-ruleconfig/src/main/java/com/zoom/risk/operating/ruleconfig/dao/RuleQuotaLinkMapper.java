package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.RuleQuotaLink;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

import java.util.List;
import java.util.Map;

@ZoomiBatisRepository(value="ruleQuotaLinkMapper")
public interface RuleQuotaLinkMapper {
 
    RuleQuotaLink selectByPrimaryKey(Long id);
	
	public void batchMapInsert(Map<String, Object> map);
	
	public void delByRuleId(long ruleId);
	
	public void delByRuleIds(List<Long> ruleIdList);
	
	public Integer selectQuotaCount(long quotaId);
	
	public Integer selectRuleCount(long ruleId);

	public Integer selectCountBy(String sql);
}
