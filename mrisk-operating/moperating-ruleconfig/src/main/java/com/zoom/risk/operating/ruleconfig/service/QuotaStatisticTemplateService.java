package com.zoom.risk.operating.ruleconfig.service;

import com.zoom.risk.operating.ruleconfig.common.ConditionUtils;
import com.zoom.risk.operating.ruleconfig.model.QuotaStatisticsTemplate;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.zoom.risk.operating.ruleconfig.common.QuotaTemplateConstants.*;

/**
 * Created by liyi8 on 2017/2/22.
 * 用于解析由统计模板创建的指标
 */
@Service("quotaStatisticService")
public class QuotaStatisticTemplateService {
    private static final Logger logger = LogManager.getLogger(QuotaStatisticTemplateService.class);

    @Autowired
    private SceneConfigService sceneConfigService;

    public String getQuota(QuotaStatisticsTemplate quotaStatisticsTemplate, String sceneNo, long templateId){
        Map<String,String> dbNameMap = sceneConfigService.getDbNameMap(sceneNo);
        StringBuilder quotaBuilder = new StringBuilder("select ");
        quotaBuilder.append(getSelect(quotaStatisticsTemplate,templateId, dbNameMap));
        quotaBuilder.append("from ").append(getTableName(sceneNo));
        StringBuilder preCondition = getPreCondition(quotaStatisticsTemplate, dbNameMap);
        quotaBuilder.append("where ").append(preCondition);
        if(StringUtils.isNotBlank(preCondition)){
            quotaBuilder.append("and ");
        }
        quotaBuilder.append(getTimeCondition(quotaStatisticsTemplate));
        StringBuilder filters = ConditionUtils.getSqlCondition(quotaStatisticsTemplate.getConditions(), quotaStatisticsTemplate.getIsAnd(), dbNameMap);
        if(StringUtils.isNotBlank(filters)){
            quotaBuilder.append(" and ").append(filters);
        }
        quotaBuilder.append(getOrder(quotaStatisticsTemplate));
        quotaBuilder.append(getPage(quotaStatisticsTemplate));
        return quotaBuilder.toString();
    }

    /**
     * templateId 1:频度模板
     *            2:求和模板
     *            3:明细模板
     * 解析select部分。
     * @param quotaStatisticsTemplate
     * @return
     */
    private StringBuilder getSelect(QuotaStatisticsTemplate quotaStatisticsTemplate, long templateId, Map<String, String> dbNameMap){
        StringBuilder selectBuilder = new StringBuilder();
        if(templateId == 1){ //频度统计模板
            selectBuilder.append(getSelectCount(quotaStatisticsTemplate, dbNameMap));
        }else if(StringUtils.isNotBlank(quotaStatisticsTemplate.getFunction()) &&
                TEMPLATE_FUNCTION_STRING_LIST.equals(quotaStatisticsTemplate.getFunction())){ //字符串list类型，即返回明细
            if ( StringUtils.isEmpty(quotaStatisticsTemplate.getSecondAttr())){
                selectBuilder.append(" count(1) ");
            }else {
                selectBuilder.append(" distinct ").append(dbNameMap.get(quotaStatisticsTemplate.getSecondAttr())).append(" ");
            }
        }else if(StringUtils.isNotBlank(quotaStatisticsTemplate.getComputeType())){
            selectBuilder.append(quotaStatisticsTemplate.getFunction()).append("(").append(dbNameMap.get(quotaStatisticsTemplate.getComputeField())).append(") ");
        }
        return selectBuilder;
    }

    private  StringBuilder getSelectCount(QuotaStatisticsTemplate quotaStatisticsTemplate, Map<String, String> dbNameMap ){
        StringBuilder selectBuilder = new StringBuilder();
        switch (quotaStatisticsTemplate.getComputeType()){
            case COMPUTE_TYPE_COUNT:
                selectBuilder.append("count(1) ");
                break;
            case COMPUTE_TYPE_AOSSIATE:
                if(StringUtils.isNotBlank(quotaStatisticsTemplate.getSecondAttr())){
                    selectBuilder.append("count(distinct ").append(dbNameMap.get(quotaStatisticsTemplate.getSecondAttr())).append(") ");
                }else{
                    selectBuilder.append("count(1) ");
                }
                break;
            case COMPUTE_TYPE_DISTINCT:
                if(StringUtils.isNotBlank(quotaStatisticsTemplate.getSecondAttr())){
                    selectBuilder.append("count(1) ");
                }else{
                    selectBuilder.append("count(distinct ").append(dbNameMap.get(quotaStatisticsTemplate.getPrimaryAttr())).append(") ");
                }
                break;
            default:
                logger.warn("failed to get select compute type,quotaStatisticsTemplate:"+ quotaStatisticsTemplate.toString());
        }
        return selectBuilder;
    }

    private StringBuilder getTableName(String sceneNo){
        return new StringBuilder(TABLE_PREFIX+sceneNo).append(" ");
    }

    /**
     * 解析属性相关条件
     * @param quotaStatisticsTemplate
     * @return
     */
    private StringBuilder getPreCondition(QuotaStatisticsTemplate quotaStatisticsTemplate, Map<String,String> dbNameMap){
        StringBuilder preCondition = new StringBuilder("");
        if(StringUtils.isNotBlank(quotaStatisticsTemplate.getComputeType()) && quotaStatisticsTemplate.getComputeType().equals(COMPUTE_TYPE_DISTINCT)){
            return preCondition;
        }
        preCondition.append(dbNameMap.get(quotaStatisticsTemplate.getPrimaryAttr())).append(" = ")
                .append("'${").append(quotaStatisticsTemplate.getPrimaryAttr()).append("}' ");
        if(StringUtils.isNotBlank(quotaStatisticsTemplate.getFunction()) && TEMPLATE_FUNCTION_STRING_LIST.equals(quotaStatisticsTemplate.getFunction())){
            return preCondition;
        }
        if(StringUtils.isNotBlank(quotaStatisticsTemplate.getSecondAttr()) && quotaStatisticsTemplate.getComputeType().equals(COMPUTE_TYPE_COUNT)){
            preCondition.append("and ");
            preCondition.append(dbNameMap.get(quotaStatisticsTemplate.getSecondAttr())).append(" = ")
                    .append("'${").append(quotaStatisticsTemplate.getSecondAttr()).append("}' ");
        }
        return preCondition;
    }

    /**
     * 解析时间片条件
     * @param quotaStatisticsTemplate
     * @return
     */
    private StringBuilder getTimeCondition(QuotaStatisticsTemplate quotaStatisticsTemplate){
        StringBuilder timeCondition = new StringBuilder();
        //当
        if(quotaStatisticsTemplate.getTimeShardType() == TIME_SHARD_CURR){
           timeCondition.append(quotaStatisticsTemplate.getTimeShardUnit()).append("(risk_date) = ")
                   .append(quotaStatisticsTemplate.getTimeShardUnit()).append("(SYSDATE()) ");
           return timeCondition;
        }

        //近。。。
        if(quotaStatisticsTemplate.getTimeShardUnit().equals("month")){ //每个月暂定为30天
            timeCondition.append("risk_date > date_sub('${riskDate}', interval ")
                    .append(quotaStatisticsTemplate.getTimeShardValue() * 30).append(" day) ");
        }else{
            timeCondition.append("risk_date > date_sub('${riskDate}', interval ")
                    .append(quotaStatisticsTemplate.getTimeShardValue()).append(" ").append(quotaStatisticsTemplate.getTimeShardUnit())
                    .append(") ");
        }
        return timeCondition;
    }

    private StringBuilder getOrder(QuotaStatisticsTemplate quotaStatisticsTemplate){
        StringBuilder orderBuilder = new StringBuilder();
        if(StringUtils.isNotBlank(quotaStatisticsTemplate.getOrderField())){
            orderBuilder.append(" order by ").append(quotaStatisticsTemplate.getOrderField());
            if(StringUtils.isNotBlank(quotaStatisticsTemplate.getOrder())){
                orderBuilder.append(" ").append(quotaStatisticsTemplate.getOrder()).append(" ");
            }
        }
        return orderBuilder;
    }

    private StringBuilder getPage(QuotaStatisticsTemplate quotaStatisticsTemplate){
        StringBuilder pageBuilder = new StringBuilder();
        if(StringUtils.isNotBlank(quotaStatisticsTemplate.getLimit())){
            pageBuilder.append("limit ").append(quotaStatisticsTemplate.getLimit());
            if(StringUtils.isNotBlank(quotaStatisticsTemplate.getOffset())){
                pageBuilder.append("offset ").append(quotaStatisticsTemplate.getOffset());
            }
        }
        return pageBuilder;
    }
}
