/**
 *
 */
package com.zoom.risk.platform.roster.api.impl;


import com.zoom.risk.platform.ctr.util.LsManager;
import com.zoom.risk.platform.roster.api.RosterInnerApiService;
import com.zoom.risk.platform.roster.vo.Rosters;
import com.zoom.risk.platform.roster.service.RosterService;
import com.zoom.risk.platform.roster.vo.Restriction;
import com.zoom.risk.platform.engine.utils.DBSelector;
import com.zoom.risk.platform.sharding.dbsharding.ServiceExecutor;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.Clock;
import java.util.*;

/**
 * Created by jiangyulin on 2015/4/6.
 */
@Service("rosterInnerApiService")
public class RosterInnerApiServiceImpl implements RosterInnerApiService {
    private static final Logger logger = LogManager.getLogger(RosterInnerApiServiceImpl.class);

    @Resource(name = "sessionManager")
    private SessionManager sessionManager;

    @Resource(name = "rosterService")
    private RosterService rosterService;

    private Map<String,Set<Integer>> sceneAppliedRosterMap = new HashMap<>();

    private Timestamp lastAccessTime = null;

    public Map<String,Object> innerVerifyHitRoster(String sceneNo, List<Rosters> queryList){
        LsManager.getInstance().check();
        long time = Clock.systemUTC().millis();
        Map<String, Object> resultMap = new HashMap<>();
        Set<Integer> appliedRosterTypes = sceneAppliedRosterMap.get(sceneNo);
        boolean isHitBlackList = false;
        if ( appliedRosterTypes != null && ( !appliedRosterTypes.isEmpty() ) ){
            Integer counts = sessionManager.runWithSession(new ServiceExecutor<Integer>() {
                public Integer execute() {
                    int temp = 0;
                    for( Integer rosterBusiType : appliedRosterTypes) {
                        temp += rosterService.verifyHitRoster(rosterBusiType, queryList);
                        if (temp > 0 ){
                            resultMap.put(Rosters.BLACK_BUSI_TYPE_KEY,rosterBusiType);
                            resultMap.put(Rosters.BLACK_KEY,1);
                            break;
                        }
                    }
                    return temp;
                }
            }, DBSelector.DS_KEY_HOLDER_OPERATING);
            if (counts > 0 ){
                isHitBlackList = true;
            }
        }
        logger.info("InnerVerifyHitRoster service take: {} ms and hit black list: {}", Clock.systemUTC().millis() - time, isHitBlackList );
        return resultMap;
    }

    /**
     * 黑名单服务
     * @param rosterBusiType
     * @param queryList
     * @return
     */
    public Map<String,Object> verifyHitRoster(Integer rosterBusiType, List<Rosters> queryList){
        Map<String, Object> resultMap = Collections.emptyMap();
        Integer counts = sessionManager.runWithSession(new ServiceExecutor<Integer>() {
                        public Integer execute() {
                            return rosterService.verifyHitRoster(rosterBusiType, queryList);
                        }
                    }, DBSelector.DS_KEY_HOLDER_OPERATING);
        if (counts > 0 ){
            resultMap = new HashMap<>();
            resultMap.put(Rosters.BLACK_KEY,1);
        }
        return resultMap;
    }


    @PostConstruct
    public void refresh(){
        try{
            Timestamp lastTime = sessionManager.runWithSession(new ServiceExecutor<Timestamp>() {
                public Timestamp execute() {
                    return rosterService.findLastSceneAppliedRoster();
                }
            }, DBSelector.DS_KEY_HOLDER_OPERATING);
            if ( lastAccessTime == null || ( lastTime != null && lastTime.after(lastAccessTime) ) ) {
                List<Restriction> list = sessionManager.runWithSession(new ServiceExecutor<List<Restriction>>() {
                    public List<Restriction> execute() {
                        return rosterService.getSceneAppliedRoster();
                    }
                }, DBSelector.DS_KEY_HOLDER_OPERATING);
                Map<String, Set<Integer>> tempMap = new HashMap<>();
                list.forEach((restriction) -> {
                    Set<Integer> set = tempMap.get(restriction.getSceneNo());
                    if (set == null) {
                        set = new HashSet<>();
                        set.add(restriction.getRosterType());
                        tempMap.put(restriction.getSceneNo(), set);
                    } else {
                        set.add(restriction.getRosterType());
                    }
                });
                sceneAppliedRosterMap = tempMap;
                lastAccessTime = lastTime;
                logger.info("reloadRosterTypeConfig has reload all data");
            }else{
                logger.info("reloadRosterTypeConfig does nothing");
            }
        }catch (Exception e){
            logger.error("",e);
        }
    }


    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public void setRosterService(RosterService rosterService) {
        this.rosterService = rosterService;
    }
}
