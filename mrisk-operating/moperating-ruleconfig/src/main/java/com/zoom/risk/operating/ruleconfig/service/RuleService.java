package com.zoom.risk.operating.ruleconfig.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.ruleconfig.dao.QuotaMapper;
import com.zoom.risk.operating.ruleconfig.dao.RuleMapper;
import com.zoom.risk.operating.ruleconfig.model.RiskNumber;
import com.zoom.risk.operating.ruleconfig.model.Rule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import static com.zoom.risk.operating.ruleconfig.common.Constants.STATUS_DRAFT;

@Service("ruleService")
public class RuleService {

    private static final Logger logger = LogManager.getLogger(RuleService.class);

    @Autowired
    private RuleMapper ruleMapper;
    @Autowired
    private RuleQuotaLinkService ruleQuotaLinkService;
    @Autowired
    private QuotaMapper quotaMapper;
    @Autowired
    private RiskNumberService riskNumberService;
    @Autowired
    private RuleConditionService ruleConditionService;

    public List<Rule> selectByPolicyId(long policyId) {
        try {
            return ruleMapper.selectByPolicyId(policyId);
        } catch (Exception e) {
            logger.error(e);
        }
        return Lists.newArrayList();
    }

    public List<Long> selectIdByPolicyId(long policyId) {
        return ruleMapper.selectIdByPolicyId(policyId);
    }

    public Rule selectById(long ruleId) {
        try {
            return ruleMapper.selectByPrimaryKey(ruleId);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public List<Rule> selectPage(long policyId, int offset, int limit) {
        try {
            Map<String, Object> pageParas = new HashMap<>();
            pageParas.put("policyId", policyId);
            pageParas.put("offset", offset);
            pageParas.put("limit", limit);
            return ruleMapper.selectPage(pageParas);
        } catch (Exception e) {
            logger.error(e);
        }
        return Lists.newArrayList();
    }

    public int selectCount(long policyId) {
        try {
            Integer count = ruleMapper.selectCount(policyId);
            return count == null ? 0 : count.intValue();
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    /**
     * 根据id更新状态
     *
     * @param type   1:按照id更新，2：按照策略id更新
     * @param id     根据type决定传什么id, 1：ruleid 2：policyId
     * @param status
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(long id, int status, int type) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("status", status);
        map.put("type", type);
        ruleMapper.updateStatus(map);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean delById(long ruleId) throws Exception {
        ruleQuotaLinkService.delByRuleId(ruleId);
        ruleMapper.deleteByPrimaryKey(ruleId);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultCode update(Rule rule) throws Exception {
        ruleMapper.updateByPrimaryKeySelective(rule);
        // 先删除关联表内容
        ruleQuotaLinkService.delByRuleId(rule.getId());
        // 添加新的关联
        if (!rule.getQuotaNoList().isEmpty()) {
            ruleQuotaLinkService.batchMapInsert(rule.getId(), getQuotaIdList(rule.getQuotaNoList()));
        }
        return ResultCode.SUCCESS;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultCode insert(Rule rule) throws Exception {
        //计算并插入序列号计算表
        int seqNo = riskNumberService.selectSeqNo(RiskNumber.ENTITY_CLASS_RULE, rule.getSceneNo());
        rule.setRuleNo(riskNumberService.getRiskNumber(seqNo + 1, rule.getSceneNo(), RiskNumber.ENTITY_CLASS_RULE));
        rule.setStatus(STATUS_DRAFT);
        riskNumberService.insertOrUpdate(RiskNumber.ENTITY_CLASS_RULE, rule.getSceneNo(), seqNo + 1);
        int res = ruleMapper.insert(rule);
        if (res == 0) {
            logger.error("插入规则表失败,请查看各字段是否正确," + rule.toString());
            throw new Exception();
        }
        //插入规则指标连接表
        if (!rule.getQuotaNoList().isEmpty()) {
            ruleQuotaLinkService.batchMapInsert(rule.getId(), getQuotaIdList(rule.getQuotaNoList()));
        }
        return ResultCode.SUCCESS;
    }

    public List<Long> getQuotaIdList(List<String> quotaNoList) throws Exception {
        if(quotaNoList.isEmpty()){
            return Lists.newArrayList();
        }
        List<Long> quotaIdList = quotaMapper.selectIdsByQuotaNos(quotaNoList);
        if (quotaIdList.isEmpty() || (quotaIdList.size() != quotaNoList.size())) {
            logger.error("指标编号有错误,指标编号为：" + quotaNoList);
            return Lists.newArrayList();
        }
        return quotaIdList;
    }

    public List<Long> getQuotaIdList(String ruleContentJson) throws Exception {
        List<String> quotaNoList = ruleConditionService.getQuotaNoList(ruleContentJson);
        if (!quotaNoList.isEmpty()) {
            return getQuotaIdList(quotaNoList);
        }
        return Lists.newArrayList();
    }

    public boolean existWeightValue(Long policyId, int weightValue, Long ruleId) throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("policyId", policyId);
        map.put("ruleId", ruleId == null ? 0 : ruleId.longValue());
        map.put("weightValue", weightValue);
        return ruleMapper.existWeightValue(map) > 0 ? true : false;
    }

    public List<Rule> selectByNamePolicyId(Long policyId, String ruleName) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("policyId", policyId);
            map.put("ruleName", ruleName);
            return ruleMapper.selectByNamePolicyId(map);
        } catch (Exception e) {
            logger.error(e);
            logger.info("查询规则出错，条件为：policyId=" + policyId + ",ruleName:" + ruleName);
        }
        return Lists.newArrayList();
    }

    public Rule selectByRuleNo(String ruleNo) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("ruleNo", ruleNo);
            return ruleMapper.selectByRuleNo(map);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 从规则内容中提取指标编号
     *
     * @param ruleContent
     * @return
     */
    public List<String> getQuotaNoList(String ruleContent) {
        Matcher matcher = Rule.QUOTA_PATTERN.matcher(ruleContent);
        Set<String> quotaNoSet = Sets.newHashSet();
        while (matcher.find()) {
            quotaNoSet.add(matcher.group().trim());
        }
        List<String> quotaNoList = Lists.newArrayList();
        quotaNoList.addAll(quotaNoSet);
        return quotaNoList;
    }

}
