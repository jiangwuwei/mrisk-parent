package com.zoom.risk.platform.scard.service.impl;

import com.zoom.risk.platform.ctr.util.LsManager;
import com.zoom.risk.platform.engine.utils.EngineConstants;
import com.zoom.risk.platform.scard.dao.SCardAllMapper;
import com.zoom.risk.platform.scard.mode.*;
import com.zoom.risk.platform.scard.service.SCardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mvel2.compiler.CompiledExpression;
import org.mvel2.compiler.ExpressionCompiler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author jiangyulin
 *May 22, 2015
 */
@Service("scardService")
public class SCardServiceImpl implements SCardService {
    private static final Logger logger = LogManager.getLogger(SCardServiceImpl.class);
    private static final Pattern pattern = Pattern.compile("^\\$|\\$$");

    @Resource
    private SCardAllMapper scardAllMapper;

    @PostConstruct
    @Transactional(readOnly = true)
    public Map<String, SCardPolicies> buildSCardEngine() {
        long time = System.currentTimeMillis();
        LsManager.getInstance().check();
        Map<String, SCardPolicies> policiesMap = new HashMap<>();
        List<SCardPolicies> sCardPoliciesList = scardAllMapper.selectSCardPolicies();
        List<SCard> sCardList = scardAllMapper.selectSCard();
        List<SCardRule> sCardRuleList = scardAllMapper.selectSCardRule();
        List<SCardRouter> sCardRouterList = scardAllMapper.selectSCardRouter();
        List<SCardParam> sCardParamList = scardAllMapper.selectSCardParam();
        List<SCardParamRoute> sCardParamRouteList = scardAllMapper.selectSCardParamRoute();
        //参数配置的路径预编译
        sCardParamRouteList.forEach((route)->{
            try{
                route.setCompiledExpression(getCompiledExpression(route.getRouteExpression(), route.getParamName()));
            }catch(Exception e){
                logger.error("Compiling param route happens error, ruleId: " + route.getId() +" , ruleContent: " + route.getRouteExpression() , e);
            }
        });
        //评分卡路由的路径预编译
        sCardRouterList.forEach((scardRouter)->{
            try{
                ExpressionCompiler compiler = new ExpressionCompiler( scardRouter.getRouterExpression() );
                CompiledExpression exp = compiler.compile();
                scardRouter.setCompiledExpression(exp);
            }catch(Exception e){
                logger.error("Compiling router happens error, ruleId: " + scardRouter.getId() +" , ruleContent: " + scardRouter.getRouterExpression() , e);
            }
        });
        //评分卡结果规则的路径预编译
        sCardRuleList.forEach((sCardRule)->{
            try{
                sCardRule.setCompiledExpression(getCompiledExpression(sCardRule.getRouteExpression(), EngineConstants.ENGINE_SCARD_SCORE));
            }catch(Exception e){
                logger.error("Compiling router happens error, ruleId: " + sCardRule.getId() +" , ruleContent: " + sCardRule.getRouteExpression() , e);
            }
        });
        for( SCardPolicies sCardPolicies : sCardPoliciesList ){
            policiesMap.put(sCardPolicies.getSceneNo(), sCardPolicies);
            for(SCardRouter sCardRouter : sCardRouterList ){
                if ( sCardPolicies.getSceneNo().equals(sCardRouter.getSceneNo())){
                    sCardPolicies.addScardRouter(sCardRouter);
                }
            }
            for(SCard sCard : sCardList ){
                if ( sCardPolicies.getSceneNo().equals(sCard.getSceneNo())){
                    sCardPolicies.addScard(sCard);
                }
                for(SCardParam sCardParam : sCardParamList ){
                    if ( sCard.getId().longValue() == sCardParam.getScardId().longValue()){
                        sCard.addSCardParam(sCardParam);
                    }
                    for (SCardParamRoute sCardParamRoute : sCardParamRouteList ){
                        if ( sCardParam.getId().longValue() == sCardParamRoute.getParamId().longValue()){
                            sCardParam.addRoute(sCardParamRoute);
                        }
                    }
                }
                for(SCardRule sCardRule : sCardRuleList ){
                    if ( sCard.getId().longValue() == sCardRule.getScardId().longValue()){
                        sCard.addSCardRule(sCardRule);
                    }
                }
            }
        }
        logger.info("SCardCacheService refreshing takes {} ms", (System.currentTimeMillis()-time));
        return policiesMap;
    }

    private CompiledExpression getCompiledExpression(String expression, String paramName){
        String routeStr = expression.trim();
        String routeExpression = null;
        if ( pattern.matcher(routeStr).find() ){
            routeExpression = routeStr.replaceAll("\\$", paramName);
        }else {
            routeExpression = routeStr.replaceAll("\\$", paramName + " && " + paramName);
        }
        ExpressionCompiler compiler = new ExpressionCompiler(routeExpression);
        CompiledExpression exp = compiler.compile();
        return exp;
    }
}
