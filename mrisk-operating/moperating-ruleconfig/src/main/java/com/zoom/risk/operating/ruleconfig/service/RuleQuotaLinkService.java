package com.zoom.risk.operating.ruleconfig.service;

import com.zoom.risk.operating.ruleconfig.dao.RuleQuotaLinkMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("ruleQuotaLinkService")
public class RuleQuotaLinkService {

    private static final Logger logger = LogManager.getLogger(RuleQuotaLinkService.class);

    @Autowired
    private RuleQuotaLinkMapper ruleQuotaLinkMapper;

    public void batchMapInsert(Long ruleId, List<Long> quotaIdList) {
        Map<String, Object> map = new HashMap<>();
        map.put("ruleId", ruleId);
        map.put("quotaList", quotaIdList);
        ruleQuotaLinkMapper.batchMapInsert(map);
    }

    public void delByRuleId(long ruleId) {
        ruleQuotaLinkMapper.delByRuleId(ruleId);
    }

    public void delByRuleIds(List<Long> ruleIdList) {
        ruleQuotaLinkMapper.delByRuleIds(ruleIdList);
    }

    public boolean existsQuota(long quotaId) {
        try {
            String sql = "select count(1)\n" +
                    "from zoom_rule_quota_link link, zoom_rule rule \n" +
                    "where \n" +
                    "\trule.id = link.rule_id and\n" +
                    "  rule.`status` != 3 and\n" +
                    "\tlink.quota_id = %s;";
            int count = ruleQuotaLinkMapper.selectCountBy(String.format(sql, quotaId));
            return count > 0 ? true : false;
        } catch (Exception e) {
            logger.error(e);
        }
        return false;
    }
}
