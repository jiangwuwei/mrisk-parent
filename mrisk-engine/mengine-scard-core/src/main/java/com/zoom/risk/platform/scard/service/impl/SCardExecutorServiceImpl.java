package com.zoom.risk.platform.scard.service.impl;

import com.zoom.risk.platform.ctr.util.LsManager;
import com.zoom.risk.platform.engine.mvel.MvelService;
import com.zoom.risk.platform.engine.utils.EngineConstants;
import com.zoom.risk.platform.scard.mode.*;
import com.zoom.risk.platform.scard.service.SCardExecutorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author jiangyulin
 *May 22, 2015
 */
@Service("scardExecutorService")
public class SCardExecutorServiceImpl implements SCardExecutorService {
    private static final Logger logger = LogManager.getLogger(SCardExecutorServiceImpl.class);
    public static final String ERRORFLAG = "ScoreCardParamRoute: ";

    @Resource(name="mvelService")
    private MvelService mvelService;

    @Override
    public SCardParamVo execute(SCardParam sCardParam, Map<String, Object> mvelMap) {
        boolean isNormalRouteExecuted = false;
        SCardParamRoute executeRoute = null;
        Float routeValue = 0F;
        for( SCardParamRoute paramRoute : sCardParam.getRouteSet() ){
            try{
                if ( mvelService.simpleEvaluate(mvelMap, paramRoute.getCompiledExpression()) ){
                    isNormalRouteExecuted = true;
                    executeRoute = paramRoute;
                    if ( paramRoute.getRouteScore().indexOf("$") > -1 ){
                        routeValue = mvelService.simpleFloatEvaluate(mvelMap, executeRoute.getRouteScore().replaceAll("\\$",paramRoute.getParamName()));
                    }else{
                        routeValue = Float.parseFloat(paramRoute.getRouteScore());
                    }
                    break;
                }
            }catch(Exception e){
                logger.error(ERRORFLAG + "Executing param route happens error, , param route : " + paramRoute, e);
            }
        }
        if ( !isNormalRouteExecuted ){
            executeRoute = createDefaultRoute(sCardParam);
            routeValue = Float.parseFloat(executeRoute.getRouteScore());
            logger.error("", ERRORFLAG +"找不到真合适的路径，参数或者该变量配置的路径有误, " + sCardParam);
        }
        return cloneOne(sCardParam, executeRoute,routeValue);
    }

    public Float executeWeight(Set<SCardParamVo> sCardParamVoList){
        Float finalValue = 0f;
        for(SCardParamVo vo : sCardParamVoList){
            Float temValue = vo.getWeightValue() * vo.getExecuteRoute().getRouteScore();
            finalValue = finalValue + temValue;
            vo.setFinalValue(temValue);
        }
        return finalValue;
    }

    public SCardRule executeRules(Set<SCardRule> sCardRules, Map<String, Object> riskInput){
        SCardRule finalSCardRule = null;
        Map<String,Object> mvelMap = new HashMap<>();
        LsManager.getInstance().check();
        mvelMap.put(EngineConstants.ENGINE_SCARD_SCORE, riskInput.get(EngineConstants.ENGINE_SCARD_SCORE));
        for( SCardRule sCardRule : sCardRules){
            try{
                if ( mvelService.simpleEvaluate(mvelMap, sCardRule.getCompiledExpression()) ){
                    finalSCardRule = sCardRule;
                    break;
                }
            }catch(Exception e){
                logger.error(ERRORFLAG + "Executing scard rule happens error, scard rule : " + sCardRule, e);
            }
        }
        return finalSCardRule;
    }


    private SCardParamRoute createDefaultRoute(SCardParam sCardParam){
        SCardParamRoute route = new SCardParamRoute();
        route.setId(0L);
        route.setRouteExpression("缺省路径(没有匹配到合适的路径)");
        route.setRouteName("缺省路径,参数或者路径配置有误!");
        route.setRouteScore(sCardParam.getDefaultScore()+"");
        route.setDbType(sCardParam.getDbType());
        route.setParamName(sCardParam.getParamName());
        route.setParamId(sCardParam.getId());
        return route;
    }

    private SCardParamVo cloneOne(SCardParam sCardParam, SCardParamRoute sCardParamRoute,Float routeValue){
        SCardParamVo sCardParamVo = new SCardParamVo();
        sCardParamVo.setId(sCardParam.getId());
        sCardParamVo.setChineseName(sCardParam.getChineseName());
        sCardParamVo.setParamName(sCardParam.getParamName());
        sCardParamVo.setParamNo(sCardParam.getParamNo());
        sCardParamVo.setDefaultScore(sCardParam.getDefaultScore());
        sCardParamVo.setFinalValue(routeValue);
        sCardParamVo.setWeightValue(sCardParam.getWeightValue());
        sCardParamVo.setScardId(sCardParam.getScardId());
        SCardParamRouteVo sCardParamRouteVo = new SCardParamRouteVo();
        sCardParamVo.setExecuteRoute(sCardParamRouteVo);
        sCardParamRouteVo.setId(sCardParamRoute.getId());
        sCardParamRouteVo.setParamId(sCardParamRoute.getParamId());
        sCardParamRouteVo.setParamName(sCardParamRoute.getParamName());
        sCardParamRouteVo.setRouteExpression(sCardParamRoute.getRouteExpression());
        sCardParamRouteVo.setRouteName(sCardParamRoute.getRouteName());
        sCardParamRouteVo.setRouteScore(routeValue);
        return  sCardParamVo;
    }
}
