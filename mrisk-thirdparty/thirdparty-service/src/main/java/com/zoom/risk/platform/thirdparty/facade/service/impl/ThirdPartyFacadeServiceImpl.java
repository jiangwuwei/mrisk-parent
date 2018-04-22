package com.zoom.risk.platform.thirdparty.facade.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.thirdparty.common.annotation.Invoker;
import com.zoom.risk.platform.thirdparty.common.service.InvokerService;
import com.zoom.risk.platform.thirdparty.common.service.ThreadLocalService;
import com.zoom.risk.platform.thirdparty.facade.service.ThirdPartyFacadeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiangyulin
 *Oct 26, 2015
 */
@Service("thirdPartyFacadeService")
public class ThirdPartyFacadeServiceImpl implements ThirdPartyFacadeService, ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LogManager.getLogger(ThirdPartyFacadeServiceImpl.class);

    private Map<String, InvokerService> invokeMaps = new ConcurrentHashMap<>();

    @Resource(name="threadLocalService")
    private ThreadLocalService threadLocalService;

    @Override
    public RpcResult<Map<String, Object>> invoke (String serviceName, Map<String, Object> riskInput){
        RpcResult<Map<String, Object>> rpcResult = null;
        String riskId = String.valueOf(riskInput.get("riskId"));
        threadLocalService.putRiskId(riskId);
        try {
            InvokerService invokerService = invokeMaps.get(serviceName);
            if ( invokerService != null ) {
                rpcResult = invokerService.invoke(riskInput);
                logger.info("Invoke service with the name {} and result is {}", serviceName, JSON.toJSONString(rpcResult));
            }else{
                logger.error("There is no match for serviceName : " + serviceName);
            }
        }finally {
            threadLocalService.remove();
        }
        return rpcResult;
    }

    public synchronized void initializedInvokeMaps(ApplicationContext applicationContext){
        invokeMaps = new ConcurrentHashMap<>();
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(Invoker.class);
        if ( beanNames.length > 0) {
            for (String bean : beanNames) {
                Object invokerObject = applicationContext.getBean(bean);
                if ( invokerObject instanceof InvokerService ) {
                    InvokerService invoker = (InvokerService)invokerObject;
                    Invoker invokerAnnotation = invoker.getClass().getAnnotation(Invoker.class);
                    invokeMaps.put(invokerAnnotation.serviceName(), invoker);
                }else{
                    logger.warn("Bean named [{}] use Invoker annotation but not implements InvokerService interface", bean );
                }
            }
            logger.info("initializedInvokeMaps messages : {} ", invokeMaps );
        }
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null){
            initializedInvokeMaps(event.getApplicationContext());
        }
    }
}
