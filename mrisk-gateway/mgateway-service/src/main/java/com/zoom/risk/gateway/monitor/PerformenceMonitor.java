package com.zoom.risk.gateway.monitor;

import com.zoom.risk.gateway.common.utils.RiskResult;
import com.zoom.risk.gateway.common.utils.GsonUtil;
import com.zoom.risk.gateway.fraud.utils.RiskConstant;
import com.zoom.risk.platform.es.service.EsActionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangyulin on 2015/3/28.
 */
@Aspect
@Component
public class PerformenceMonitor {
    private static final Logger logger = LogManager.getLogger(PerformenceMonitor.class);

    @Resource(name="riskPoolExecutor")
    private ThreadPoolTaskExecutor riskPoolExecutor;

    @Resource(name="esActionService")
    private EsActionService esActionService;

    /**
     * 监控com.henry.advertising.web.service包及其子包的所有public方法
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    @Pointcut("execution(* com.zoom.risk.gateway.service.Risk*Facade.evaluate(..))")
    private void fraudFacadePointCut() {
    }

    //声明环绕通知
    @Around("fraudFacadePointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        long takingTime = Clock.systemUTC().millis();
        Object result = null;
        try{
            result = pjp.proceed();
        }finally {
            takingTime = Clock.systemUTC().millis() - takingTime ;
            Map<String,Object> riskInput = (Map<String,Object>)pjp.getArgs()[0];
            if ( riskInput.get(RiskConstant.RISK_ID) != null  ) {
                Map<String,Object> monitorMap = this.getMap(riskInput, takingTime);
                final String finalJson = GsonUtil.getGson().toJson(monitorMap);
                riskPoolExecutor.submit(
                    ()-> {
                        logger.info("PerformaceLog : [{}] ", finalJson );
                        esActionService.dispatchEvent(finalJson);
                    }
                );
            }
        }
        return result;
    }


    private Map<String,Object> getMap(Map<String,Object> riskInput, long takingTime){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("riskType", RiskResult.RISK_TYPE_MONITOR);
        resultMap.put("takingTime", takingTime);
        resultMap.put("riskBusiType", riskInput.get("riskBusiType"));
        resultMap.put(RiskConstant.RISK_SCENE, riskInput.get(RiskConstant.RISK_SCENE));
        resultMap.put(RiskConstant.RISK_ID, riskInput.get(RiskConstant.RISK_ID));
        resultMap.put(RiskConstant.RISK_DATE, riskInput.get(RiskConstant.RISK_DATE));
        resultMap.put("riskLongDate", riskInput.get("riskLongDate"));
        resultMap.put("gatewayStatus", riskInput.get("gatewayStatus"));
        resultMap.put(RiskConstant.RISK_PLATFORM, riskInput.get(RiskConstant.RISK_PLATFORM));
        resultMap.put(RiskConstant.DEVICE_FINGERPRINT, riskInput.get(RiskConstant.DEVICE_FINGERPRINT));
        return resultMap;
    }

}
