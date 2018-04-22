package com.zoom.risk.operating.decision.service.impl;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.decision.dao.DtPoliciesMapper;
import com.zoom.risk.operating.decision.po.TPolicies;
import com.zoom.risk.operating.decision.po.TPolicy;
import com.zoom.risk.operating.decision.service.DtPoliciesService;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by liyi8 on 2017/5/24.
 */
@Service("dtPoliciesService")
public class DtPoliciesServiceImpl implements DtPoliciesService {

    private static final Logger logger  = LogManager.getLogger(DtPoliciesServiceImpl.class);

    @Autowired
    private DtPoliciesMapper dtPoliciesMapper;

    public List<TPolicies> selectAll(){
        try {
            return dtPoliciesMapper.selectAll();
        } catch (Exception e) {
            logger.error(e);
        }
        return Lists.newArrayList();
    }

    public TPolicies selectById(String sceneNo){
        try {
            return dtPoliciesMapper.selectByPrimaryKey(sceneNo);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public List<TPolicies> selectPage(String sceneNo, int offset, int limit){
        try {
            Map<String, Object> pageParas = new HashMap<>();
            pageParas.put("sceneNo", sceneNo);
            pageParas.put("offset", offset);
            pageParas.put("limit", limit);
            return dtPoliciesMapper.selectPage(pageParas);
        } catch (Exception e) {
            logger.error(e);
        }
        return Lists.newArrayList();
    }

    public int selectCount(String sceneNo){
        try {
            Integer count = dtPoliciesMapper.selectCount(sceneNo);
            return count==null?0:count.intValue();
        } catch (Exception e) {
            logger.error(e);
        }
        return 0;
    }

    public boolean exists(String sceneNo, String name) throws Exception{
        Map<String, Object> map = new HashMap<>();
        map.put("sceneNo", sceneNo);
        map.put("name", name);
        return (dtPoliciesMapper.exists(map)>0)?true:false;
    }

    public boolean update(TPolicies TPolicies){
        try {
            dtPoliciesMapper.updateByPrimaryKey(TPolicies);
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    public ResultCode delById(String sceneNo){
        try {
            dtPoliciesMapper.deleteByPrimaryKey(sceneNo);
        } catch (Exception e) {
            logger.error(e);
            return ResultCode.DB_ERROR;
        }
        return ResultCode.SUCCESS;
    }

    public boolean insert(TPolicies TPolicies) {
        try {
            dtPoliciesMapper.insert(TPolicies);
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
        return true;
    }

    /**
     * 抽取策略集中的场景号
     * @param TPoliciesList
     * @param sceneNo 前台参数
     * @return
     */
    public List<String> getSceneNoList(List<TPolicies> TPoliciesList, String sceneNo){
        Set<String> sceneNoSet = new HashSet<>();
        if(!"all".equals(sceneNo)){
            sceneNoSet.add(sceneNo);
        }else{
            for (TPolicies TPolicies : TPoliciesList) {
                sceneNoSet.add(TPolicies.getSceneNo());
            }
        }
        List<String> sceneNoList = Lists.newArrayList();
        sceneNoList.addAll(sceneNoSet);
        return sceneNoList;
    }

    public List<TPolicies> selectByName(String name, int offset, int limit){
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("policiesName", name);
            map.put("offset", offset);
            map.put("limit", limit);
            return dtPoliciesMapper.selectByName(map);
        } catch (Exception e) {
            logger.error(e);
        }
        return Lists.newArrayList();
    }

    @Override
    public List<Pair<TPolicies, List<TPolicy>>> getPoliciesPairList(List<TPolicies> policiesList, List<TPolicy> policyList) {
        List<Pair<TPolicies, List<TPolicy>>> list = Lists.newArrayList();
        for (TPolicies policies : policiesList) {
            ListIterator<TPolicy> listIterator = policyList.listIterator();
            List<TPolicy> tmpPolicyList = Lists.newArrayList();
            while(listIterator.hasNext()){
                TPolicy policy = listIterator.next();
                //匹配到场景，就把这个策略从策略列表中移除
                if(policies.getSceneNo().equals(policy.getSceneNo())){
                    tmpPolicyList.add(policy);
                    listIterator.remove();
                }
            }
            list.add(Pair.of(policies, tmpPolicyList));
        }
        return list;
    }

    @Override
    public Map<String, TPolicies> getPoliciesMap(List<TPolicies> policiesList) {
        Map<String, TPolicies> map = new HashMap<>();
        for (TPolicies policies : policiesList) {
            map.put(policies.getSceneNo(), policies);
        }
        return map;
    }
}
