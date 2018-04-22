package com.zoom.risk.platform.scard.cache.impl;

import com.zoom.risk.platform.scard.cache.SCardCacheService;
import com.zoom.risk.platform.scard.mode.EnginePolices;
import com.zoom.risk.platform.scard.mode.SCard;
import com.zoom.risk.platform.scard.mode.SCardPolicies;
import com.zoom.risk.platform.scard.mode.SCardRouter;
import com.zoom.risk.platform.scard.proxy.SCardServiceProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mvel2.MVEL;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jiangyulin
 *May 22, 2015
 */

@Service("scardCacheService")
public class SCardCacheServiceImpl implements SCardCacheService {
    private static final Logger logger = LogManager.getLogger(SCardCacheServiceImpl.class);

    private Map<String, SCardPolicies> scardMap;

    @Resource(name="scardServiceProxy")
    private SCardServiceProxy scardServiceProxy;

    @Override
    public EnginePolices getSCardPolicies(String sceneNo, Map<String,Object> cloneRiskInput) {
        EnginePolices enginePolices = new EnginePolices();
        if ( sceneNo.length() > 6 ){
            logger.warn("The sceneNo should be 4 length, the current value is " + sceneNo );
            sceneNo = sceneNo.substring(0,4);
        }
        SCardPolicies  sCardPolicies =  scardMap.get(sceneNo);
        Set<SCardRouter> routes = sCardPolicies.getRouterSet();
        if (  !routes.isEmpty() ) {
            for (SCardRouter router : routes) {
                try {
                    Boolean result = (Boolean) MVEL.executeExpression(router.getCompiledExpression(), cloneRiskInput);
                    if (result) {
                        enginePolices.setScard(this.getSCard(router, sCardPolicies.getScardList()));
                        enginePolices.setScardRouter(router);
                        break;
                    }
                } catch (Exception e) {
                    logger.error("MVEL execute expression happens error, expression:" + router.getRouterExpression(), e);
                }
            }
        }
        //2. if no one found then use max weightValue
        if ( enginePolices.getScard() == null ){
            if ( !sCardPolicies.getScardList().isEmpty()) {
                enginePolices.setScard(sCardPolicies.getScardList().get(0));
            }
        }
        return enginePolices;
    }

    @PostConstruct
    public void init(){
        scardMap = scardServiceProxy.buildSCardEngine();
    }


    public void refresh(){
        init();
    }


    private SCard getSCard(SCardRouter router, List<SCard> scardList){
        String scardId = router.getScardId()+"";
        SCard result = null;
        for(SCard sCard : scardList ){
            if ( scardId.equals(sCard.getId()+"") ){
                result = sCard;
                break;
            }
        }
        return result;
    }

}
