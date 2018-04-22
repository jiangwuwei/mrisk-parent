package com.zoom.risk.operating.ruleconfig.service;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.ruleconfig.dao.PoliciesMapper;
import com.zoom.risk.operating.ruleconfig.model.Policies;
import com.zoom.risk.operating.ruleconfig.model.Policy;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("policiesService")
public class PoliciesService {
	
	private static final Logger logger  = LogManager.getLogger(PoliciesService.class);
	
	@Autowired
	private PoliciesMapper policiesMapper;
	
	public List<Policies> selectAll(){
		try {
			return policiesMapper.selectAll();
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public Policies selectById(String sceneNo){
		try {
			return policiesMapper.selectByPrimaryKey(sceneNo);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	public List<Policies> selectPage(String sceneNo, int offset, int limit){
		try {
			Map<String, Object> pageParas = new HashMap<>();
			pageParas.put("sceneNo", sceneNo);
			pageParas.put("offset", offset);
			pageParas.put("limit", limit);
			return policiesMapper.selectPage(pageParas);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public int selectCount(String sceneNo){
		try {
			Integer count = policiesMapper.selectCount(sceneNo);
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
		return (policiesMapper.exists(map)>0)?true:false;
	}
	
	public boolean update(Policies policies){
		try {
			policiesMapper.updateByPrimaryKey(policies);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	public ResultCode delById(String sceneNo){
		try {
			policiesMapper.deleteByPrimaryKey(sceneNo);
		} catch (Exception e) {
			logger.error(e);
			return ResultCode.DB_ERROR; 
		}
		return ResultCode.SUCCESS;
	}
	
	public boolean insert(Policies policies) {
		try {
			policiesMapper.insert(policies);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	/**
	 * 抽取策略集中的场景号
	 * @param policiesList
	 * @param sceneNo 前台参数
	 * @return
	 */
	public List<String> getSceneNoList(List<Policies> policiesList, String sceneNo){
		Set<String> sceneNoSet = new HashSet<>();
		if(!"all".equals(sceneNo)){
			sceneNoSet.add(sceneNo);
		}else{
			for (Policies policies : policiesList) {
				sceneNoSet.add(policies.getSceneNo());
			}
		}
		List<String> sceneNoList = Lists.newArrayList();
		sceneNoList.addAll(sceneNoSet);
		return sceneNoList;
	}
	
	/**
	 * 获取策略集策略列表
	 * @param policiesList 策略集列表
	 * @param policyList 策略列表
	 * @param policyRouterSceneNo 有策略路由的场景号
	 * @return
	 */
	public List<Pair<Policies, List<Policy>>> getPoliciesPairList(List<Policies> policiesList,
                                                                  List<Policy> policyList, List<String> policyRouterSceneNo) {
		List<Pair<Policies, List<Policy>>> list = Lists.newArrayList();
		for (Policies policies : policiesList) {
			ListIterator<Policy> listIterator = policyList.listIterator();
			List<Policy> tmpPolicyList = Lists.newArrayList();
			while(listIterator.hasNext()){
				Policy policy = listIterator.next();
				//匹配到场景，就把这个策略从策略列表中移除
				if(policies.getSceneNo().equals(policy.getSceneNo())){
					tmpPolicyList.add(policy);
					listIterator.remove();
				}
			}
			if(policyRouterSceneNo.indexOf(policies.getSceneNo()) >= 0){
				policies.setHasPolicyRouter(true);
			}
			list.add(Pair.of(policies, tmpPolicyList));
		}
		return list;
	}
	
	public List<Policies> selectByName(String name, int offset, int limit){
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("policiesName", name);
			map.put("offset", offset);
			map.put("limit", limit);
			return policiesMapper.selectByName(map);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public Map<String, Policies> getPoliciesMap(List<Policies> policiesList){
		Map<String, Policies> map = new HashMap<>();
		for (Policies policies : policiesList) {
			map.put(policies.getSceneNo(), policies);
		}
		return map;
	}
}
