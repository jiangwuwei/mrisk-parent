package com.zoom.risk.operating.ruleconfig.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zoom.risk.operating.common.db.DBSelector;
import com.zoom.risk.operating.es.service.EsQueryService;
import com.zoom.risk.operating.es.vo.EventInputModel;
import com.zoom.risk.operating.es.vo.EventOutputModel;
import com.zoom.risk.operating.es.vo.EventRuleInfo;
import com.zoom.risk.operating.ruleconfig.common.Constants;
import com.zoom.risk.operating.ruleconfig.dao.MonitorMapper;
import com.zoom.risk.operating.ruleconfig.model.*;
import com.zoom.risk.operating.ruleconfig.model.*;
import com.zoom.risk.operating.ruleconfig.vo.SceneVo;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("monitorService")
public class MonitorService {
    private static final Logger logger = LogManager.getLogger(MonitorService.class);
    public static final Map<String, String> productTypeMap = new HashMap<>();
    public static final String RISK_BUSI_TYPE_DT = "2"; // 决策树业务类型
    public static final String RISK_BUSI_TYPE_SC = "3"; // 评分卡模型
    @Autowired
    private ScenesService scenesService;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private MonitorMapper monitorMapper;
    @Autowired
    private SessionManager sessionManager;

    @Resource(name="esQueryService")
    private EsQueryService esQueryService;

    static {
        productTypeMap.put("1", "定期下单");
        productTypeMap.put("3", "保险下单");
        productTypeMap.put("4", "活期下单");
        productTypeMap.put("5", "钱罐转入下单");
        productTypeMap.put("6", "钱罐转出下单");
    }

    /**
     * 从es那边获取事件列表原始数据
     *
     * @param input
     * @return
     */
    public Map<String, Object> getEventList(EventInputModel input) {
        EventOutputModel output = esQueryService.queryEvents(input);
        List<Map<String, Object>> list = output.getResultList();
        List<Map<String, Object>> resultList = Lists.newArrayList();
        for (Map<String, Object> map : list) {
            resultList.add(formatEvent(map));
        }
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("totalSize", output.getTotalSize());
        resultMap.put("list", resultList);
        return resultMap;
    }

    /**
     * 提取事件监控所需要的数据
     *
     * @param map es里的原始数据
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private Map<String, Object> formatEvent(Map<String, Object> map) {
        Map<String, Object> eventMap = Maps.newHashMap();
        eventMap.putAll(map);
        //地理位置
        Map<String, String> geoIpMap = (Map<String, String>) map.get("geoIp");
        String geoAddr = "";
        if (geoIpMap != null) {
            String provinceName = geoIpMap.get("provinceName") == null ? "" : geoIpMap.get("provinceName");
            String cityName = geoIpMap.get("cityName") == null ? "" : geoIpMap.get("cityName");
            geoAddr = provinceName + "-" + cityName;
        }
        eventMap.put("geoAddr", geoAddr);
        //命中规则数
        List hitRules = (List) map.get("hitRules");
        eventMap.put("hitRuleCount", (hitRules == null) ? 0 : hitRules.size());
        String sceneNo = (String) map.get("scene");
        sceneNo = getRealSceneNo(sceneNo);
        //场景名称
        Scenes scenes = scenesService.selectById(sceneNo);
        eventMap.put("scenesName", scenes == null ? "" : scenes.getName());
        return eventMap;
    }

    /**
     * 规则监控列表
     *
     * @param sceneNo
     * @param riskDate
     * @return
     */
    public List<Map<String, String>> getRuleList(String sceneNo, String riskDate) {
        EventRuleInfo eventRuleInfo = esQueryService.queryRuleDetail(sceneNo, riskDate);
        List<Map<String, String>> list = Lists.newArrayList();
        Scenes scenes = scenesService.selectById(sceneNo);
        DecimalFormat decimalFormat = new DecimalFormat("#0.00%");
        for (EventRuleInfo.RuleInfo ruleInfo : eventRuleInfo.getRuleInfos()) {
            Map<String, String> map = Maps.newHashMap();
            Rule rule = ruleService.selectByRuleNo(ruleInfo.getRuleNo());
            String ruleName = "";
            if ( rule != null){
                ruleName = rule.getName();
            }else{
                ruleName = commonRules.get(ruleInfo.getRuleNo());
            }
            map.put("ruleName", ruleName == null ? "" : ruleName);
            map.put("ruleNo", ruleInfo.getRuleNo());
            map.put("sceneName", scenes.getName());
            map.put("totalEvents", "" + eventRuleInfo.getEventTotalCount());
            map.put("hitCount", "" + ruleInfo.getHitCount());
            map.put("hitRatio", decimalFormat.format((new Double(ruleInfo.getHitCount())) / eventRuleInfo.getEventTotalCount()));
            list.add(map);
        }
        return list;
    }


    private static final Map<String, String> commonRules = new HashMap<>();
    static{
        commonRules.put("R0000_0000000","黑名单规则");
    }
    /**
     * 获取事件详情
     *
     * @param riskId
     * @param riskDate
     * @return
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getEventDetail(String riskId, String riskDate) {
        Map<String, Object> resultMap = Maps.newHashMap();
        Map<String, Object> originalData = esQueryService.queryEventDetail(riskDate, riskId);
        String riskBusiType = String.valueOf(originalData.get("riskBusiType"));
        resultMap.put("riskBusiType", riskBusiType);
        this.fillEnvironmentAttr(resultMap, originalData);
        if (RISK_BUSI_TYPE_DT.equals(riskBusiType)) {
            this.fillExecuteRoute(resultMap, originalData);
            this.fillDtEventAttr(resultMap, originalData);
        } else if (RISK_BUSI_TYPE_SC.equals(riskBusiType)) {
            this.fillScardEventAttr(resultMap, originalData);
        } else {
            this.fillEventTop(resultMap, originalData);
            this.fillAntiFraudEventAttr(resultMap, originalData);
        }
        return resultMap;
    }

    public Map<String, Long> getRelationship(String parameterName, String parameterValue, String riskDate) {
        EventRuleInfo info = esQueryService.queryRelationship(parameterName, parameterValue, riskDate);
        Map<String, Long> result = new HashMap<>();
        info.getRuleInfos().forEach(
                (ruleInfo) -> {
                    result.put(ruleInfo.getRuleNo(), ruleInfo.getHitCount());
                }
        );
        return result;
    }

    /**
     * 填充决策树执行路径
     *
     * @param resultMap
     * @param originalData
     */
    private void fillExecuteRoute(Map<String, Object> resultMap, Map<String, Object> originalData) {
        //生成指标值map
        List<Map<String, Object>> quotaStrValueList = (List) originalData.get("quotaStrValues");
        Map<String, Object> quotaVlueMap = Maps.newHashMap();
        for (Map<String, Object> map : quotaStrValueList) {
            quotaVlueMap.put((String) map.get("paramName"), map.get("value"));
        }
        List<Map<String, Object>> executeRouteList = (List) originalData.get("executingRoute");
        boolean policyIdFlag = false;
        StringBuilder hitNodeIdStr = new StringBuilder();
        for (Map<String, Object> map : executeRouteList) {
            map.put("actualValue", quotaVlueMap.get(map.get("paramName")));
            hitNodeIdStr.append(map.get("id")).append(",");
            if (!policyIdFlag) {
                resultMap.put("dt_policy_id", map.get("policyId"));
                policyIdFlag = true;
            }
        }
        if (hitNodeIdStr.length() > 0) {
            hitNodeIdStr.deleteCharAt(hitNodeIdStr.length() - 1);
        }
        resultMap.put("hitNodeIdStr", hitNodeIdStr.toString());
        resultMap.put("executeRouteList", executeRouteList);
    }

    /**
     * 填充决策树事件属性
     *
     * @param resultMap
     * @param originalData
     */
    private void fillDtEventAttr(Map<String, Object> resultMap, Map<String, Object> originalData) {
        List<String> excludedAttrList = Lists.newArrayList();
        excludedAttrList.add("token");
        excludedAttrList.add("uid");
        excludedAttrList.add("sourceIp");
        excludedAttrList.add("platform");
        excludedAttrList.add("debug");
        excludedAttrList.add("riskBusiType");
        excludedAttrList.add("riskLongDate");
        excludedAttrList.add("quotaStrValues");
        excludedAttrList.add("gatewayStatus");
        excludedAttrList.add("executingRoute");
        List<String> priorityAttrList = Lists.newArrayList();
        priorityAttrList.add("scene");
        priorityAttrList.add("decisionCode");
        priorityAttrList.add("failReason");
        priorityAttrList.add("actionCode");
        final Map<String, Object> keyMap = this.prepareDtKeyMap();
        final Map<String, Object> valueMap = this.prepareDtValueMap((String.valueOf(originalData.get("scene"))));
        List<SceneVo> sceneVoList = Lists.newArrayList();
        List<SceneVo> tmpSceneVoList = Lists.newArrayList();
        originalData.forEach((key, value) -> {
            if (excludedAttrList.indexOf(key) == -1 && value != null) {
                String param_value = String.valueOf(value);
                if ("riskStatus".equals(key)) {
                    int riskStatus = Integer.valueOf(param_value).intValue();
                    param_value = riskStatus == 0 ? "初始状态" : (riskStatus == 1 ? "通过" : "失败");
                }
                if ("decisionCode".equals(key)) {
                    int decisionCode = Integer.valueOf(param_value).intValue();
                    param_value = decisionCode == 1 ? "通过" : (decisionCode == 2 ? "人工审核" : "拒绝");
                }
                if (DtDim.ACTION_CODE.equals(key) || DtDim.FAIL_REASON.equals(key) || "scene".equals(key)) {
                    Object tmpValue = valueMap.get(String.valueOf(value));
                    param_value = (tmpValue == null) ? "" : String.valueOf(tmpValue);
                }
                String param_key = keyMap.get(key) == null ? key : String.valueOf(keyMap.get(key));
                if ( ! ( "geoIp".equals(param_key) || "riskType".equals(param_key) || "riskBusiType".equals(param_key) ) ) {
                    if (priorityAttrList.contains(String.valueOf(key))) {
                        sceneVoList.add(new SceneVo(param_key, param_key, param_value));
                    } else {
                        tmpSceneVoList.add(new SceneVo(param_key, param_key, param_value));
                    }
                }
            }
        });
        sceneVoList.addAll(tmpSceneVoList);
        resultMap.put("sceneVoList", sceneVoList);
    }

    /**
     * 决策树属性value中文对照表
     *
     * @param sceneNo
     * @return
     */
    private Map<String, Object> prepareDtValueMap(String sceneNo) {
        Map<String, Object> map = new HashMap<>();
        try {
            Scenes scenes = scenesService.selectById(this.getRealSceneNo(sceneNo));
            map.put(sceneNo, scenes.getName());
            List<DtDim> dimList = monitorMapper.selectAllDtDim();
            if (dimList != null) {
                dimList.forEach(dtDim -> {
                    if (dtDim.getCode().equals(DtDim.DV) || dtDim.getCode().equals(DtDim.REFUSE_REASON)) {
                        map.put(dtDim.getId() + "", dtDim.getName());
                    }
                });
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return map;
    }

    /**
     * 决策树属性key中文对照表
     *
     * @return
     */
    private Map<String, Object> prepareDtKeyMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("riskStatus", "业务状态");
        map.put("decisionCode", "决策码");
        map.put("scene", "场景");
        map.put("riskDate", "业务发生时间");
        map.put("riskId", "风控识别号");
        map.put("actionCode", "动作指令");
        map.put("failReason", "拒绝原因");
        try {
            List<DtParamTemplate> paramTemplates = sessionManager.runWithSession(() -> {
                return monitorMapper.selectAllDtParamTemplate();
            }, Constants.DATA_SOUCE_DATA_SER);
            if (paramTemplates != null) {
                paramTemplates.forEach(dtParamTemplate -> map.put(dtParamTemplate.getName(), dtParamTemplate.getChineseName()));
            }
            List<DtQuotaTemplate> quotaTemplateList = monitorMapper.selectAllDtQuotaName();
            if (quotaTemplateList != null) {
                quotaTemplateList.forEach(quotaTemplate -> map.put(quotaTemplate.getParamName(), quotaTemplate.getChineseName()));
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return map;
    }

    /**
     * 决策树属性key中文对照表
     *
     * @return
     */
    private Map<String, Object> prepareSCKeyMap(Long scardId) {
        Map<String, Object> map = new HashMap<>();
        map.put("riskStatus", "业务状态");
        map.put("decisionCode", "决策码");
        map.put("scene", "场景");
        map.put("riskDate", "业务发生时间");
        map.put("riskId", "风控识别号");
        map.put("sourceIp", "请求IP");
        map.put("actionCode", "动作指令");
        map.put("failReason", "拒绝原因");
        //
        map.put("scardRuleFinal","评分卡规则结果");
        map.put("scardScore","评分卡结果");
        map.put("extendChallengeNumber","挑战随机数");

        try {
            List<Map<String,Object>> paramTemplates = sessionManager.runWithSession(() -> {
                return monitorMapper.selectMapForScardParam(scardId);
            }, DBSelector.OPERATING_MASTER_DB);
            paramTemplates.forEach((m)->{
                map.put((String)m.get("param_name"), m.get("chinese_name"));
            });
        } catch (Exception e) {
            logger.error(e);
        }
        return map;
    }

    /**
     * 填充环境属性
     *
     * @param resultMap
     * @param originalData
     */
    private void fillEnvironmentAttr(Map<String, Object> resultMap, Map<String, Object> originalData) {
        resultMap.put("uid", originalData.get("uid"));
        resultMap.put("token", originalData.get("token"));
        resultMap.put("platform", originalData.get("platform"));
        resultMap.put("sourceIp", originalData.get("sourceIp"));
        resultMap.put("device_fingerprint", originalData.get("device_fingerprint"));
        resultMap.put("device_fpip", originalData.get("device_fpip"));
    }

    /**
     * 填充反欺诈事件顶部数据
     *
     * @param resultMap
     * @param originalData
     */
    private void fillEventTop(Map<String, Object> resultMap, Map<String, Object> originalData) {
        //顶部数据
        List<Map<String, Object>> hitRules = (List<Map<String, Object>>) originalData.get("hitRules");
        resultMap.put("hitCount", hitRules.size());
        //命中规则
        this.getHitRuleList(originalData, hitRules, resultMap);
        resultMap.put("decisionCode", originalData.get("decisionCode"));
        Map<String, String> policyMap = (Map<String, String>) originalData.get("policy");
        resultMap.put("finalValue", (policyMap == null) ? "" : policyMap.get("finalValue"));
    }

    /**
     * 填充反欺诈事件属性
     *
     * @param resultMap
     */
    private void fillAntiFraudEventAttr(Map<String, Object> resultMap, Map<String, Object> originalData) {
        List<String> excludedAttrList = Lists.newArrayList();
        excludedAttrList.add("token");
        excludedAttrList.add("uid");
        excludedAttrList.add("sourceIp");
        excludedAttrList.add("platform");
        excludedAttrList.add("decisionCode");
//		excludedAttrList.add("riskId");
//		excludedAttrList.add("projectFlag");
        String sceneNo = (String) originalData.get("scene");
        List<String> priorityAttrList = Lists.newArrayList(); //事件优先展示属性
        priorityAttrList.add("deviceFingerprint");
        priorityAttrList.add("deviceFpip");
        priorityAttrList.add("riskDate");
        priorityAttrList.add("riskStatus");
        resultMap.put("sceneVoList", scenesService.getSceneVoList(getRealSceneNo(sceneNo), excludedAttrList, originalData, priorityAttrList));
    }

    /**
     * 填充反欺诈事件属性
     *
     * @param resultMap
     */
    private void fillScardEventAttr(Map<String, Object> resultMap, Map<String, Object> originalData) {
        Map<String,Object> scardMap = (Map<String,Object>)originalData.get("scard");
        String scardId = String.valueOf( scardMap.get("id"));
        List<String> excludedAttrList = Lists.newArrayList();
        excludedAttrList.add("token");
        excludedAttrList.add("uid");
        excludedAttrList.add("platform");
        excludedAttrList.add("decisionCode");
        excludedAttrList.add("riskStatus");
        excludedAttrList.add("riskType");
        excludedAttrList.add("riskLongDate");
        excludedAttrList.add("riskBusiType");

        List<String> detailAttrList = Lists.newArrayList();
        detailAttrList.add("scard");
        detailAttrList.add("scardRuleRoute");
        detailAttrList.add("scardRouter");
        detailAttrList.add("scardParamRoutes");

        List<String> eventAttrList = Lists.newArrayList();
        eventAttrList.add("scene");
        eventAttrList.add("riskDate");
        eventAttrList.add("riskId");
        eventAttrList.add("sourceIp");
        eventAttrList.add("extendChallengeNumber");

        List<String> scardAttrList = Lists.newArrayList();

        scardAttrList.add("scardRuleFinal");
        scardAttrList.add("scardScore");

        //系统参数
        List<SceneVo> sceneVoList = Lists.newArrayList();
        Map<String,SceneVo> scardAttrVoMap = Maps.newHashMap();
        Map<String,Object> detailAttVoMap = Maps.newHashMap();
        Map<String,Object> eventAttVoMap = Maps.newHashMap();
        final Map<String, Object> keyMap = this.prepareSCKeyMap(new Long(scardId));
        originalData.forEach((key, value) -> {
            if ( excludedAttrList.indexOf(key) == -1 && value != null) {
                if ( scardAttrList.indexOf(key) > -1 ) {
                    String param_value = String.valueOf(value);
                    String param_key = keyMap.get(key) == null ? key : String.valueOf(keyMap.get(key));
                    scardAttrVoMap.put(key, new SceneVo(param_key, key, param_value));
                }else if ( detailAttrList.indexOf(key) > -1 ){
                    Object param_value = value;
                    detailAttVoMap.put(key, param_value);
                }else if ( eventAttrList.indexOf(key) > -1 ){
                    Object param_value = value;
                    eventAttVoMap.put(key, param_value);
                }else{
                    String param_value = String.valueOf(value);
                    String param_key = keyMap.get(key) == null ? key : String.valueOf(keyMap.get(key));
                    sceneVoList.add(new SceneVo(param_key, param_key, param_value));
                }
            }
        });
        resultMap.put("eventAttVoMap",eventAttVoMap);
        resultMap.put("detailAttVoMap",detailAttVoMap);
        resultMap.put("scardAttrVoMap",scardAttrVoMap);
        resultMap.put("sceneVoList", sceneVoList);
    }

    /**
     * 解析命中规则列表
     *
     * @param originalData
     * @param hitRules
     * @param resultMap    事件详情结果map
     */
    @SuppressWarnings("unchecked")
    private void getHitRuleList(Map<String, Object> originalData, List<Map<String, Object>> hitRules, Map<String, Object> resultMap) {
        List<Map<String, String>> quotasValue = (List<Map<String, String>>) originalData.get("quotasValue");
        Map<String, Pair<String, Object>> quotaNoMap = Maps.newHashMap();
        if (quotasValue != null) {
            for (Map<String, String> map : quotasValue) {
                quotaNoMap.put((String) map.get("quotaNo"), Pair.of((String) map.get("name"),map.get("quotaValueStr")));
            }
            for (Map<String, Object> map : hitRules) {
                //命中的规则对应的指标
                List<String> quotaNoList = ruleService.getQuotaNoList((String) map.get("ruleContent"));
                if (!quotaNoList.isEmpty()) {
                    List<Pair<String, Object>> quotaVoList = Lists.newArrayList();
                    StringBuilder hitQuotaString = new StringBuilder("");
                    for (String quotaNo : quotaNoList) {
                        quotaVoList.add(quotaNoMap.get(quotaNo));
                        Pair<String, Object> pair = quotaNoMap.get(quotaNo);
                        hitQuotaString = hitQuotaString.append(pair.getKey()).append(":").append(pair.getValue()).append(",");
                    }
                    map.put("hitQuotaString", hitQuotaString.length() != 0 ? hitQuotaString.substring(0, hitQuotaString.length() - 1) : hitQuotaString);
                    map.put("hitQuotas", quotaVoList);
                    map.put("hitQuotasCount", quotaVoList.size());
                }
                //命中规则-指令操作-用于查看是否有增强校验
                Rule rule = ruleService.selectByRuleNo((String) map.get("ruleNo"));
                if (rule == null) {
                    logger.info(map);
                }
                map.put("actionCode", rule == null ? "" : rule.getActionCode());
            }
        }
        resultMap.put("hitRules", hitRules);
    }

    /**
     * 兼容6位场景号
     *
     * @param sceneNo
     * @return
     */
    public String getRealSceneNo(String sceneNo) {
        if (StringUtils.isNotBlank(sceneNo) && sceneNo.length() == 6) {
            sceneNo = sceneNo.substring(0, 4);
        }
        return sceneNo;
    }
}
