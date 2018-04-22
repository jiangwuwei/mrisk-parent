package com.zoom.risk.operating.scard.service;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.scard.dao.SCardPoliciesMapper;
import com.zoom.risk.operating.scard.model.SCard;
import com.zoom.risk.operating.scard.model.SCardPolicies;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Service("scardPoliciesService")
public class SCardPoliciesService {
	
	private static final Logger logger  = LogManager.getLogger(SCardPoliciesService.class);
	
	@Autowired
	private SCardPoliciesMapper scardPoliciesMapper;
	
	public List<SCardPolicies> selectAll(){
		try {
			return scardPoliciesMapper.selectAll();
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public SCardPolicies selectById(String sceneNo){
		try {
			return scardPoliciesMapper.selectByPrimaryKey(sceneNo);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	public List<SCardPolicies> selectPage(String sceneNo, int offset, int limit){
		try {
			Map<String, Object> pageParas = new HashMap<>();
			pageParas.put("sceneNo", sceneNo);
			pageParas.put("offset", offset);
			pageParas.put("limit", limit);
			return scardPoliciesMapper.selectPage(pageParas);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public int selectCount(String sceneNo){
		try {
			Integer count = scardPoliciesMapper.selectCount(sceneNo);
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
		return (scardPoliciesMapper.exists(map)>0)?true:false;
	}
	
	public boolean update(SCardPolicies policies){
		try {
			scardPoliciesMapper.updateByPrimaryKey(policies);
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	public ResultCode delById(String sceneNo){
		try {
			scardPoliciesMapper.deleteByPrimaryKey(sceneNo);
		} catch (Exception e) {
			logger.error(e);
			return ResultCode.DB_ERROR; 
		}
		return ResultCode.SUCCESS;
	}
	
	public boolean insert(SCardPolicies policies) {
		try {
			scardPoliciesMapper.insert(policies);
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
	public List<String> getSceneNoList(List<SCardPolicies> policiesList, String sceneNo){
		Set<String> sceneNoSet = new HashSet<>();
		if(!"all".equals(sceneNo)){
			sceneNoSet.add(sceneNo);
		}else{
			for (SCardPolicies policies : policiesList) {
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
	public List<Pair<SCardPolicies, List<SCard>>> getPoliciesPairList(List<SCardPolicies> policiesList,
                                                                      List<SCard> policyList, List<String> policyRouterSceneNo) {
		List<Pair<SCardPolicies, List<SCard>>> list = Lists.newArrayList();
		for (SCardPolicies policies : policiesList) {
			ListIterator<SCard> listIterator = policyList.listIterator();
			List<SCard> tmpPolicyList = Lists.newArrayList();
			while(listIterator.hasNext()){
				SCard policy = listIterator.next();
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
	
	public List<SCardPolicies> selectByName(String name, int offset, int limit){
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("policiesName", name);
			map.put("offset", offset);
			map.put("limit", limit);
			return scardPoliciesMapper.selectByName(map);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public Map<String, SCardPolicies> getPoliciesMap(List<SCardPolicies> policiesList){
		Map<String, SCardPolicies> map = new HashMap<>();
		for (SCardPolicies policies : policiesList) {
			map.put(policies.getSceneNo(), policies);
		}
		return map;
	}
}
