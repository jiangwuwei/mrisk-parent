package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.Rule;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

import java.util.List;
import java.util.Map;

@ZoomiBatisRepository(value="ruleMapper")
public interface RuleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Rule record);

    int insertSelective(Rule record);

    Rule selectByPrimaryKey(Long id);
    
    List<Rule> selectByPolicyId(long policyId);
    
    List<Long> selectIdByPolicyId(long policyId);

    int updateByPrimaryKeySelective(Rule record);

    int updateByPrimaryKey(Rule record);
    
    /**
     * 查询分页规则
     * @param pageParas policyId, limit, offset
     * @return
     */
    List<Rule> selectPage(Map<String, Object> pageParas);
    
    /**
     * 根据策略id和规则名称搜索规则
     * @param pageParas policyId，ruleName
     * @return
     */
    List<Rule> selectByNamePolicyId(Map<String, Object> pageParas);
    
    Integer selectCount(Long policyId);
    
    int updateStatus(Map<String, Object> pageParas);
    
    /**
     * 检查同一场景同一执行优先级的规则的数量
     * @param map
     * @return
     */
    int existWeightValue(Map<String, Object> map);
    
    /**
     * 按照规则编号查询
     * @param ruleNo
     * @return
     */
    Rule selectByRuleNo(Map<String, Object> map);
}