package com.zoom.risk.gateway.hitrule.framework;

import com.zoom.risk.gateway.hitrule.common.HitRuleEvent;
import com.zoom.risk.gateway.hitrule.service.HitRuleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author jiangyulin
 *May 1, 2016
 */
@Service("hitRuleActionListener")
public class HitRuleActionListener implements ApplicationListener<HitRuleEvent> {
    private static final Logger logger = LogManager.getLogger(HitRuleActionListener.class);

    @Resource(name="hitRuleFramework")
    private HitRuleFramework hitRuleFramework;

    @Async
    public void onApplicationEvent(HitRuleEvent event) {
        Map<String,HitRuleService> actions = hitRuleFramework.getHitRuleActions();
        for( String actionCode : event.getActionCodes()){
            HitRuleService service = actions.get(actionCode);
            try{
                service.doAction(event.getRiskInput());
            }catch (Exception e){
                logger.error("",e);
            }
        }
    }

}
