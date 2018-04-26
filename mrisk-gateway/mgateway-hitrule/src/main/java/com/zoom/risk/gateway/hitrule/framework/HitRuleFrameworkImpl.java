package com.zoom.risk.gateway.hitrule.framework;

import com.zoom.risk.gateway.hitrule.common.HitRuleAnnotation;
import com.zoom.risk.gateway.hitrule.common.HitRuleEvent;
import com.zoom.risk.gateway.hitrule.service.HitRuleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author jiangyulin
 *May 1, 2018
 */
@Service("hitRuleFramework")
public class HitRuleFrameworkImpl implements HitRuleFramework, ApplicationListener<ContextRefreshedEvent>, ApplicationContextAware {
    private static final Logger logger = LogManager.getLogger(HitRuleFrameworkImpl.class);
    private Map<String,HitRuleService> actionMap = new HashMap<>();
    private ApplicationContext applicationContext;
    
    public synchronized void initializedExtendList(ApplicationContext applicationContext) {
        String[] beanNames = applicationContext.getBeanNamesForAnnotation(HitRuleAnnotation.class);
        if (beanNames.length > 0) {
            for (String bean : beanNames) {
                Object extendBean = applicationContext.getBean(bean);
                if (extendBean instanceof HitRuleService) {
                    HitRuleService hitRuleAction = (HitRuleService) extendBean;
                    String actionCode  = hitRuleAction.getClass().getAnnotation(HitRuleAnnotation.class).actionCode();
                    if (actionCode != null && actionCode.length() > 0) {
                        actionMap.put(actionCode, hitRuleAction);
                    }
                } else {
                    logger.warn("Bean named [{}] use HitRuleAnnotation annotation but not implements HitRuleService interface", bean);
                }
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            initializedExtendList(event.getApplicationContext());
        }
    }

    @Override
    public void publishEvent(Set<String> actionCodes, Map<String, Object> riskInput) {
        if ( actionCodes != null && actionCodes.size() > 0 ) {
            try {
                applicationContext.publishEvent(new HitRuleEvent(actionCodes, riskInput));
            }catch (Exception e){
                logger.error("",e);
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Map<String,HitRuleService> getHitRuleActions(){
        return this.actionMap;
    }
}
