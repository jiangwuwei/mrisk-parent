package com.zoom.risk.operating.ruleconfig.service;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.ruleconfig.common.Constants;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.ruleconfig.dao.QuotaMapper;
import com.zoom.risk.operating.ruleconfig.model.Policies;
import com.zoom.risk.operating.ruleconfig.model.Quota;
import com.zoom.risk.operating.ruleconfig.model.RiskNumber;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("quotaService")
public class QuotaService {
	@Autowired
	private QuotaMapper quotaMapper;
	@Autowired
	private RiskNumberService riskNumberService;
	
	private static final Logger logger  = LogManager.getLogger(QuotaService.class);
	
	public List<Quota> selectBySceneNo(String sceneNo, int status){
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("sceneNo", sceneNo);
			map.put("status", status);
			return quotaMapper.selectBySceneNo(map);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public Quota selectById(long quotaId){
		try {
			return quotaMapper.selectByPrimaryKey(quotaId);
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}
	
	public List<Quota> selectPage(String sceneNo, int offset, int limit){
		try {
			Map<String, Object> pageParas = new HashMap<>();
			pageParas.put("sceneNo", sceneNo);
			pageParas.put("offset", offset);
			pageParas.put("limit", limit);
			return quotaMapper.selectPage(pageParas);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public int selectCount(String sceneNo){
		try {
			Integer count = quotaMapper.selectCount(sceneNo);
			return count==null?0:count.intValue();
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}
	
	public ResultCode updateStatus(long quotaId, int status){
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("quotaId", quotaId);
			map.put("status", status);
			quotaMapper.updateStatus(map);
		} catch (Exception e) {
			logger.error(e);
			return ResultCode.DB_ERROR; 
		}
		return ResultCode.SUCCESS;
	}
	
	public ResultCode delById(long quotaId){
		try {
			quotaMapper.deleteByPrimaryKey(quotaId);
		} catch (Exception e) {
			logger.error(e);
			return ResultCode.DB_ERROR; 
		}
		return ResultCode.SUCCESS;
	}
	
	public boolean update(Quota quota){
		try {
			quotaMapper.updateByPrimaryKeySelective(quota);
	 	} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public boolean insert(Quota quota) throws Exception{
		int seqNo = riskNumberService.selectSeqNo(RiskNumber.ENTITY_CLASS_QUOTA, quota.getSceneNo());
		//获取指标编号
		quota.setQuotaNo(riskNumberService.getRiskNumber(seqNo+1, quota.getSceneNo(),RiskNumber.ENTITY_CLASS_QUOTA));
		//指标取消拟草状态，插入即生效
		quota.setStatus(Constants.STATUS_IN_EFFECT);
		//插入或者更新指标序列号
		riskNumberService.insertOrUpdate(RiskNumber.ENTITY_CLASS_QUOTA, quota.getSceneNo(), seqNo+1);
		//插入指标
		quotaMapper.insert(quota);
		return true;
	}
	
	public List<Long> selectIdsByQuotaNos(List<String> quotaNoList){
		try {
			List<Long> quotaIdList = quotaMapper.selectIdsByQuotaNos(quotaNoList);
			return quotaIdList;
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	public List<Quota> selectByName(String name, int offset, int limit){
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("quotaName", name);
			map.put("offset", offset);
			map.put("limit", limit);
			return quotaMapper.selectByName(map);
		} catch (Exception e) {
			logger.error(e);
		}
		return Lists.newArrayList();
	}
	
	/**
	 * 匹配策略集-指标列表
	 * @param quotaList 按照sceneNo排序的指标列表
	 * @return
	 */
	public List<Pair<Policies, List<Quota>>> getPoliciesQuotaList(Map<String, Policies> map, List<Quota> quotaList, String sceneNo){
		List<Pair<Policies, List<Quota>>> list = Lists.newArrayList();
		if(!quotaList.isEmpty()){//将现有指标和策略集对应起来
			List<Quota> tmpQuotaList = Lists.newArrayList();
			String tmpSceneNo = "";
			for (Quota quota : quotaList) {
				if(!quota.getSceneNo().equals(tmpSceneNo)){
					if(StringUtils.isNoneBlank(tmpSceneNo)){
						list.add(Pair.of(map.get(tmpSceneNo), tmpQuotaList));
					}
					tmpQuotaList = Lists.newArrayList();
					map.remove(tmpSceneNo);
					tmpSceneNo = quota.getSceneNo();
				}
				tmpQuotaList.add(quota);
			}
			list.add(Pair.of(map.get(tmpSceneNo), tmpQuotaList));
			map.remove(tmpSceneNo);
		}
		if("all".equals(sceneNo)){ //添加没有指标的策略集
			getPoliciesQuotaList(map, list);
		}else if (map.get(sceneNo) != null) { //当前策略集没有指标的情况
			list.add(Pair.of(map.get(sceneNo), Lists.newArrayList()));
		}
		return list;
	}
	
	public void getPoliciesQuotaList(Map<String, Policies> map, List<Pair<Policies, List<Quota>>> list){
		Set<String> keyset = map.keySet();
		for (String string : keyset) {
			list.add(Pair.of(map.get(string), Lists.newArrayList()));
		}
	}
}