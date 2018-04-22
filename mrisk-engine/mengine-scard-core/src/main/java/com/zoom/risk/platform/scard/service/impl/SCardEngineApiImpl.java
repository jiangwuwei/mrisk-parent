package com.zoom.risk.platform.scard.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.platform.ctr.util.LsManager;
import com.zoom.risk.platform.engine.utils.EngineConstants;
import com.zoom.risk.platform.es.service.EsActionService;
import com.zoom.risk.platform.scard.api.SCardEngineApi;
import com.zoom.risk.platform.scard.api.ScoreCardResponse;
import com.zoom.risk.platform.scard.cache.SCardCacheService;
import com.zoom.risk.platform.scard.jsonvo.*;
import com.zoom.risk.platform.scard.mode.*;
import com.zoom.risk.platform.scard.service.SCardExecutorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author jiangyulin
 *May 22, 2015
 */
@Service("scardEngineApi")
public class SCardEngineApiImpl implements SCardEngineApi {
    private static final Logger logger = LogManager.getLogger(SCardEngineApiImpl.class);
    public static final String TAKINGTIME = "takingTime";

    @Resource(name="scardCacheService")
    private SCardCacheService scardCacheService;

    @Resource(name="scardExecutorService")
    private SCardExecutorService scardExecutorService;

    @Resource(name="esActionService")
    private EsActionService esActionService;

    @Resource(name="esClientThreadPoolExecutor")
    private ThreadPoolTaskExecutor esClientThreadPoolExecutor;

    @Override
    public ScoreCardResponse evaluate(Map<String, Object> riskInput) {
        ScoreCardResponse sCardResponse = new ScoreCardResponse();
        String sceneNo = riskInput.get(EngineConstants.ENGINE_SCENE).toString().substring(0, 4);
        LsManager.getInstance().check();
        long time = System.currentTimeMillis();
        EnginePolices enginePolices = scardCacheService.getSCardPolicies(sceneNo, riskInput);
        if ( enginePolices.getScard()== null || enginePolices.getScard().getParamSet().isEmpty()){
            return new ScoreCardResponse(ScoreCardResponse.RESPONSE_NOT_FOUND,"没有配置有效的策略或者参数路径");
        }
        Set<SCardParamVo> scoreCardRoutes = new HashSet<>();
        Float engineScardScore = 0f;
        try{
            SCard sCard = enginePolices.getScard();
            Set<SCardParam> paramList = sCard.getParamSet();
            //执行评分卡中所有的参数以及命中的路径
            for (SCardParam sCardParam : paramList ){
                SCardParamVo sCardParamVo = scardExecutorService.execute(sCardParam, riskInput);
                scoreCardRoutes.add(sCardParamVo);
                engineScardScore = engineScardScore + sCardParamVo.getExecuteRoute().getRouteScore();
            }
            //如果参数中有权值，重新计算评分结果
            if (SCard.WEIGHT_YES_1 == sCard.getWeightFlag().intValue() ){
                engineScardScore = scardExecutorService.executeWeight(scoreCardRoutes);
            }

            sCardResponse.setEngineScardScore(engineScardScore);
            riskInput.put(EngineConstants.ENGINE_SCARD_SCORE, engineScardScore);
            //如果配置有后续的评分结果规则，执行转换结果
            SCardRule sCardRule = scardExecutorService.executeRules(sCard.getRuleSet(), riskInput);
            if ( sCardRule != null ) {
                sCardResponse.setEngineScardRuleFinal(sCardRule.getFinalResult());
                riskInput.put(EngineConstants.ENGINE_SCARD_RULE_FINAL, sCardRule.getFinalResult());
                riskInput.put(EngineConstants.ENGINE_SCARD_RULE_ROUTE, this.convertRuleRoute(sCardRule));
            }
            riskInput.put(EngineConstants.ENGINE_SCARD_ROUTER, this.convertRouter(enginePolices.getScardRouter()));
            riskInput.put(EngineConstants.ENGINE_SCARD, this.convertScoreCard(sCard));
            riskInput.put(EngineConstants.ENGINE_SCARD_PARAM_ROUTES, this.convertExecutedRoute(scoreCardRoutes));
            logger.info("ScoreCard sCardResponse is {} ", JSON.toJSONString(sCardResponse));
            logger.info("ScoreCard riskInput is {} ", JSON.toJSONString(riskInput));
        }catch (Exception e){
            logger.error("",e);
        }finally {
            this.sendKafkaMessage(riskInput);
        }
        return sCardResponse;
    }

    private void sendKafkaMessage(final Map<String, Object> riskInput){
        esClientThreadPoolExecutor.submit(() -> {
            String detailJson = null;
            try {
                detailJson = JSON.toJSONString(riskInput);
                logger.info("Risk Scard Engine send kafka message [{}]", detailJson);
                esActionService.dispatchEvent(detailJson);
            } catch (Exception e) {
                logger.error("ScardEngineApi converting to json or sending kafka message happen error", e);
            }
            logger.info("RuleEngineApi evaluating and sending detail json : [{}]", detailJson);
        });
    }

    private List<ScoreCardParam> convertExecutedRoute(Set<SCardParamVo> paramVos){
        List<ScoreCardParam> paramSet = new ArrayList<>();
        for(SCardParamVo vo : paramVos ){
            ScoreCardParam param = new ScoreCardParam();
            paramSet.add(param);
            param.setChineseName(vo.getChineseName());
            param.setDefaultScore(vo.getDefaultScore());
            param.setWeightValue(vo.getWeightValue());
            param.setParamName(vo.getParamName());
            param.setParamNo(vo.getParamNo());
            param.setFinalScore(vo.getFinalValue());
            ScoreCardParamRoute route = new ScoreCardParamRoute();
            param.setScoreCardParamRoute(route);
            SCardParamRouteVo routeVo = vo.getExecuteRoute();
            route.setParamName(routeVo.getParamName());
            route.setRouteName(routeVo.getRouteName());
            route.setRouteExpression(routeVo.getRouteExpression());
            route.setRouteScore(routeVo.getRouteScore());
        }
        return paramSet;
    }

    private ScoreCardRouter convertRouter(SCardRouter scardRouter){
        ScoreCardRouter router = null;
        if ( scardRouter!= null ) {
            router = new ScoreCardRouter();
            router.setName(scardRouter.getName());
            router.setRouterExpression(scardRouter.getRouterExpression());
            router.setRouterNo(scardRouter.getRouterNo());
            router.setSceneNo(scardRouter.getSceneNo());
        }
        return router;
    }

    private ScoreCard convertScoreCard(SCard sCard){
        ScoreCard scoreCard = new ScoreCard();
        scoreCard.setId(sCard.getId());
        scoreCard.setName(sCard.getName());
        scoreCard.setPercentageFlag(sCard.getPercentageFlag());
        scoreCard.setScardNo(sCard.getScardNo());
        scoreCard.setSceneNo(sCard.getSceneNo());
        return scoreCard;
    }

    private ScoreCardRuleRoute convertRuleRoute(SCardRule sCardRule){
        ScoreCardRuleRoute ruleRoute = new ScoreCardRuleRoute();
        ruleRoute.setFinalResult(sCardRule.getFinalResult());
        ruleRoute.setRouteExpression(sCardRule.getRouteExpression());
        ruleRoute.setRouteName(sCardRule.getRouteName());
        return ruleRoute;
    }
}
