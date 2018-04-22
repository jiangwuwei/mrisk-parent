package com.zoom.risk.platform.decision.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zoom.risk.platform.ctr.util.LsManager;
import com.zoom.risk.platform.decision.api.DTreeEngineApi;
import com.zoom.risk.platform.decision.api.DTreeResponse;
import com.zoom.risk.platform.decision.cache.DTreeCacheService;
import com.zoom.risk.platform.decision.po.DBNode;
import com.zoom.risk.platform.decision.po.DecisionTreeWrapper;
import com.zoom.risk.platform.decision.service.DTreeExecutorService;
import com.zoom.risk.platform.decision.vo.DBNodeVo;
import com.zoom.risk.platform.decision.vo.QutoaInfo;
import com.zoom.risk.platform.es.service.EsActionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by jiangyulin on 2017/5/17.
 */
@Service("dtreeEngineApi")
public class DTreeEngineApiImpl implements DTreeEngineApi {
    private static final Logger logger = LogManager.getLogger(DTreeEngineApiImpl.class);
    //事件处理的状态 默认为 0;  0: 成功 1: 数据校验异常 2: 调用风控引擎异常
    public static final String GATEWAY_STATUS_OK = "0";
    public static final String GATEWAY_STATUS_PARAM_ERROR = "1";
    public static final String GATEWAY_STATUS_SYSTEM_ERROR = "2";

    public static final String TAKINGTIME = "takingTime";
    public static final String EXECUTING_ROUTE = "executingRoute";
    public static final String DEBUG = "debug";
    public static final String QUOTA_STR_VALUES = "quotaStrValues";


    @Resource(name="dtreeExecutorService")
    private DTreeExecutorService dtreeExecutorService;

    @Resource(name="dtreeCacheService")
    private DTreeCacheService dtreeCacheService;

    @Resource(name="esClientThreadPoolExecutor")
    private ThreadPoolTaskExecutor esClientThreadPoolExecutor;

    @Resource(name="esActionService")
    private EsActionService esActionService;

    @Override
    public DTreeResponse evaluate(Map<String, Object> riskInput) {
        long time = System.currentTimeMillis();
        LsManager.getInstance().check();
        String sceneNo = String.valueOf(riskInput.get("scene")).substring(0,4);
        riskInput.put("decisionCode","3");
        DecisionTreeWrapper rootWapper = dtreeCacheService.getPolicyDecisionTree(sceneNo);
        DTreeResponse response = this.validateDecisionTree(rootWapper,riskInput);
        String gatewayStatus = GATEWAY_STATUS_SYSTEM_ERROR;
        List<DBNode> resultNodes = new ArrayList<>();
        List<QutoaInfo> quotaValueList = new ArrayList<>();
        try {
            if ( response.isOK() ) {
                Map<String, Object> executionMap = this.shadowClone(riskInput);
                dtreeExecutorService.executeDecisionTree(rootWapper.getRoot(), executionMap, resultNodes, quotaValueList, sceneNo);
                DBNode leaf = resultNodes.get(resultNodes.size() - 1);
                riskInput.put("decisionCode",leaf.getDecisionCode()+"");
                if ( leaf.getActionCode() != null ) {
                    riskInput.put("actionCode", Arrays.asList(leaf.getActionCode()) );
                }else{
                    riskInput.put("actionCode", Collections.emptyList());
                }
                riskInput.put("failReason",leaf.getReason());
                response.setDecisionCode(leaf.getDecisionCode());
                response.setActionCode(leaf.getActionCode());
                response.setReason(leaf.getReason());
                response.getExtendedData().put(TAKINGTIME, System.currentTimeMillis() - time);
                gatewayStatus = GATEWAY_STATUS_OK;
            }
        } catch (Throwable e) {
            gatewayStatus = GATEWAY_STATUS_SYSTEM_ERROR;
            response.setResponseCode(DTreeResponse.RESPONSE_SERVER_ERROR);
            logger.error("DTreeEngineApi happens error, ", e);
        }finally {
            //fix if no route or error happens when display the executed routes
            List<DBNodeVo> voRoutes = new ArrayList<>();
            {
                resultNodes.forEach((node) -> {
                    voRoutes.add(node.copyOne());
                });
                riskInput.put(EXECUTING_ROUTE, voRoutes);
                riskInput.put(QUOTA_STR_VALUES, quotaValueList);
            }
            if ( riskInput.containsKey(DEBUG)) {
                response.getExtendedData().put(EXECUTING_ROUTE, voRoutes);
                Map<String, Object> quotaInfos = new HashMap<>();
                quotaValueList.forEach((q) -> {
                    quotaInfos.put(q.getParamName(), q.getValue() + "#" + q.getTakingTime());
                });
                response.getExtendedData().put(QUOTA_STR_VALUES, quotaInfos);
            }
            riskInput.put("gatewayStatus",gatewayStatus);
            this.sendKafkaMessage(riskInput, System.currentTimeMillis() - time);
            return response;
        }

    }

    private void sendKafkaMessage(final Map<String, Object> riskInput, long totalTime){
        esClientThreadPoolExecutor.submit(() -> {
            String detailJson = null;
            try {
                detailJson = JSON.toJSONString(riskInput, SerializerFeature.SkipTransientField, SerializerFeature.DisableCircularReferenceDetect);
                logger.info("DTreeEngineApi kafka message :{}", detailJson);
                esActionService.dispatchEvent(detailJson);
            } catch (Exception e) {
                logger.error("DTreeEngineApi converting to json or sending kafka message happen error", e);
            }
            logger.info("DTreeEngineApi evaluating and sending takes {} ms, detail json : [{}]", totalTime, detailJson);
        });
    }


    private Map<String,Object> shadowClone(Map<String,Object> finalRiskInput ){
        return (Map<String,Object>)((HashMap<String,Object>)finalRiskInput).clone();
    }

    private DTreeResponse validateDecisionTree(DecisionTreeWrapper rootWapper, Map<String, Object> riskInput){
        if (rootWapper == null) {
            logger.info("DTreeEngineApi can not find decision tree for riskInput : {}", riskInput);
            return DTreeResponse.get404Error("can not find decision tree");
        }
        return new DTreeResponse();
    }

}
