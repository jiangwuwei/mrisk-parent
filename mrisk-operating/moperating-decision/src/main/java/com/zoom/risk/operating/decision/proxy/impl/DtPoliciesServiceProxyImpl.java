package com.zoom.risk.operating.decision.proxy.impl;

import com.zoom.risk.operating.common.db.DBSelector;
import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.decision.po.TPolicies;
import com.zoom.risk.operating.decision.po.TPolicy;
import com.zoom.risk.operating.decision.proxy.DtPoliciesServiceProxy;
import com.zoom.risk.operating.decision.service.DtPoliciesService;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by liyi8 on 2015/5/24.
 */
@Service("dtPoliciesServiceProxy")
public class DtPoliciesServiceProxyImpl implements DtPoliciesServiceProxy {

    private static final Logger logger = LogManager.getLogger(DtPoliciesServiceProxyImpl.class);

    @Resource(name="sessionManager")
    private SessionManager sessionManager;
    @Resource(name="dtPoliciesService")
    private DtPoliciesService dtPoliciesService;

    public List<TPolicies> selectAll(){
        return sessionManager.runWithSession( ()->{
            return dtPoliciesService.selectAll();
        }, DBSelector.OPERATING_MASTER_DB);
    }

    public TPolicies selectById(String sceneNo){
        return sessionManager.runWithSession( ()->{
            return dtPoliciesService.selectById(sceneNo);
        }, DBSelector.OPERATING_MASTER_DB);
    }

    public List<TPolicies> selectPage(String sceneNo, int offset, int limit){
        return sessionManager.runWithSession( ()->{
            return dtPoliciesService.selectPage(sceneNo, offset, limit);
        }, DBSelector.OPERATING_MASTER_DB);
    }

    public int selectCount(String sceneNo){
        return sessionManager.runWithSession( ()->{
            return dtPoliciesService.selectCount(sceneNo);
        }, DBSelector.OPERATING_MASTER_DB);
    }

    public boolean exists(String sceneNo, String name) {
        return sessionManager.runWithSession( ()->{
            try {
                return dtPoliciesService.exists(sceneNo, name);
            } catch (Exception e) {
                logger.error(e);
            }
            return false;
        }, DBSelector.OPERATING_MASTER_DB);
    }

    public boolean update(TPolicies tPolicies){
        return sessionManager.runWithSession( ()->{
            return dtPoliciesService.update(tPolicies);
        }, DBSelector.OPERATING_MASTER_DB);
    }

    public ResultCode delById(String sceneNo){
        return sessionManager.runWithSession( ()->{
            return dtPoliciesService.delById(sceneNo);
        }, DBSelector.OPERATING_MASTER_DB);
    }

    public boolean insert(TPolicies tPolicies){
        return sessionManager.runWithSession( ()->{
            return dtPoliciesService.insert(tPolicies);
        }, DBSelector.OPERATING_MASTER_DB);
    }

    public List<TPolicies> selectByName(String name, int offset, int limit){
        return sessionManager.runWithSession( ()->{
            return dtPoliciesService.selectByName(name, offset, limit);
        }, DBSelector.OPERATING_MASTER_DB);
    }

    public List<String> getSceneNoList(List<TPolicies> policiesList, String sceneNo){
        return sessionManager.runWithSession( ()->{
            return dtPoliciesService.getSceneNoList(policiesList, sceneNo);
        }, DBSelector.OPERATING_MASTER_DB);
    }

    public List<Pair<TPolicies, List<TPolicy>>> getPoliciesPairList(List<TPolicies> policiesList,
                                                                    List<TPolicy> policyList){
        return sessionManager.runWithSession( ()->{
            return dtPoliciesService.getPoliciesPairList(policiesList, policyList);
        }, DBSelector.OPERATING_MASTER_DB);
    }
    public Map<String, TPolicies> getPoliciesMap(List<TPolicies> policiesList){
        return sessionManager.runWithSession( ()->{
            return dtPoliciesService.getPoliciesMap(policiesList);
        }, DBSelector.OPERATING_MASTER_DB);
    }
}
