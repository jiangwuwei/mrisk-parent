package com.zoom.risk.operating.ruleconfig.common;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import static com.zoom.risk.operating.ruleconfig.common.Constants.*;
import static com.zoom.risk.operating.ruleconfig.common.QuotaTemplateConstants.TEMPLATE_TYPE_STRING_LIST;

/**
 * Created by liyi8 on 2017/2/22.
 */
public class ConditionUtils {

    /**
     * 将condition解析为sql表达式
     * @param conditions
     * @param isAnd
     * @return
     */
    public static StringBuilder getSqlCondition(List<Condition> conditions, boolean isAnd, Map<String, String> dbNameMap){
        if(conditions == null){
            return new StringBuilder();
        }
        return getConditions(conditions, isAnd, false, dbNameMap);
    }


    /**
     * 解析条件
     * @param conditions
     * @param isAnd 条件链接符
     * @param isMvl 是否是mvl表达式
     * @return
     */
    public static StringBuilder  getConditions(List<Condition> conditions, boolean isAnd, boolean isMvl, Map<String, String> dbNameMap){
        StringBuilder resultCondition = new StringBuilder();
        if((conditions==null) || (conditions!=null && conditions.isEmpty())){
            return resultCondition;
        }
        boolean isFirst = true;
        ListIterator<Condition> listIterator = conditions.listIterator();
        while(listIterator.hasNext()){
            Condition condition = listIterator.next();
            if(condition.getAttr() == null){ //前端删除引起的空条件
                listIterator.remove();
                continue;
            }
            if(isFirst){
                resultCondition.append(" (");
                isFirst = false;
            }else{
                //条件连接符号
                resultCondition.append(getConnector(isMvl, isAnd));
            }

            if(isMvl){
                analysisMvl(resultCondition, condition);
            }else{
                analysisSql(resultCondition, condition, dbNameMap);
            }
        }
        if(!isFirst){
            resultCondition.append(" )");
        }
        return resultCondition;
    }

    /**
     * 获取连接符
     * @param isMvl true:mvl表达式。false:sql表达式
     * @param isAnd
     * @return
     */
    public static String getConnector(boolean isMvl, boolean isAnd){
        if(isMvl){
            return isAnd?" && ":" || ";
        }
        return isAnd?" and ":" or ";
    }

    /**
     * 将条件解析为mvl表达式
     * @param content
     * @param condition
     */
    public static void analysisMvl(StringBuilder content, Condition condition){
        String attr = condition.getAttr();
        String value = condition.getValue();
        switch (condition.getOper()){
            case "isEmpty":
                content.append(String.format(MVEL_TOOLS_EMPTY, attr));
                break;
            case "contains":
                //字符串list的属性特殊处理
                if(StringUtils.isNotBlank(condition.getAttrType())&&
                        TEMPLATE_TYPE_STRING_LIST.equals(condition.getAttrType())){
                    content.append(String.format(MVEL_TOOLS_LIST_CONTAINS, attr, getContainsValue(condition)));
                }else{
                    content.append(String.format(MVEL_TOOLS_CONTAINS, attr, getContainsValue(condition)));
                }
                break;
            case "notContains":
                //字符串list的属性特殊处理
                if(StringUtils.isNotBlank(condition.getAttrType())&&
                        TEMPLATE_TYPE_STRING_LIST.equals(condition.getAttrType())){
                    content.append(String.format(MVEL_TOOLS_LIST_NOT_CONTAINS, attr, getContainsValue(condition)));
                }else{
                    content.append(String.format(MVEL_TOOLS_NOT_CONTAINS, attr, getContainsValue(condition)));
                }
                break;
            case "equal":
                content.append(String.format(MVEL_TOOLS_COND_FORMAT, condition.getAttr(), "==","'"+condition.getValue()+"'"));
                break;
            default:
                //字符串list的属性特殊处理
                if(StringUtils.isNotBlank(condition.getAttrType())&&
                        TEMPLATE_TYPE_STRING_LIST.equals(condition.getAttrType())){
                    attr = condition.getAttr()+".size()";
                }
                if(StringUtils.isNotBlank(condition.getExtent())){
                    value += "-"+ condition.getExtent();
                }
                content.append(String.format(MVEL_TOOLS_COND_FORMAT, attr, condition.getOper(), value));
        }
    }

    private static String getContainsValue(Condition condition){
        if(!condition.getIsValueInput()){
            return condition.getValue();
        }
         return "'"+condition.getValue()+"'";
    }

    /**
     * 将条件解析为sql表达式
     * @param content
     * @param condition
     */
    public static void analysisSql(StringBuilder content, Condition condition, Map<String,String> dbNameMap){
        String value = condition.getValue();
        if(!condition.getIsValueInput()){
            value = "${"+condition.getValue()+"}";
        }
        if("isEmpty".equals(condition.getOper())){
            content.append(String.format(SQL_IS_EMPTY, dbNameMap.get(condition.getAttr()),dbNameMap.get(condition.getAttr())));
        }else if ("contains".equals(condition.getOper()) ) {
            content.append(String.format(SQL_CONTAINS, dbNameMap.get(condition.getAttr()), "%"+value+"%"));
        }else if ("notContains".equals(condition.getOper()) ) {
            content.append(String.format(SQL_NOT_CONTAINS, dbNameMap.get(condition.getAttr()), "%"+value+"%"));
        }else if ("equal".equals(condition.getOper()) ) {
            content.append(String.format(MVEL_TOOLS_COND_FORMAT, dbNameMap.get(condition.getAttr()), "=","'"+value+"'"));
        }else{
            content.append(String.format(MVEL_TOOLS_COND_FORMAT, dbNameMap.get(condition.getAttr()), condition.getOper(), value));
        }
    }
}
