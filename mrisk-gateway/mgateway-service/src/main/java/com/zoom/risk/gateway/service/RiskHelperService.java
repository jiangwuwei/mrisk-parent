package com.zoom.risk.gateway.service;

import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.engine.api.DecisionRule;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2015/12/7.
 */
public interface RiskHelperService {
    public static final String  HIT_RULE_ID = "uuid";
    public static final String  HIT_RULE_NAME = "name";
    public static final String  HIT_RULE_RULE_NO = "ruleNo";
    public static final String  HIT_RULE_DECISION = "decision";

    public static final String RISK_LONG_DATE = "riskLongDate";

    /**
     * @param riskScene
     * prepare necessary data for risk gateway
     * @param riskData
     * @return
     */
    public String createRiskSystemData(String riskScene, Map<String, Object> riskData, String riskBusiType);

    /**make sure riskId has been put into return result should rule engine happen errors
     * @param result
     * @param riskId
     */
    public void putRiskIfPossible(RpcResult<Map<String,Object>> result, String riskId);

    /**
     * convert hit rules to map model to adjust exiting mechanism
     * @param hitRules
     * @return
     */
    public List<Map<String,Object>> convertHitRules(List<DecisionRule> hitRules);

    /** to get riskLongDate, in normal case it should be always a non null long value
     * @param riskData
     * @return
     */
    public String getRiskLongDate(Map<String,Object> riskData);


    public RpcResult<Map<String,Object>> getSucccessulResult(String riskScene);

}
