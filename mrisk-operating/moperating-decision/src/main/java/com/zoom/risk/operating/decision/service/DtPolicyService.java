package com.zoom.risk.operating.decision.service;

import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.decision.po.TPolicy;

import java.util.List;

public interface DtPolicyService {
	
	public List<TPolicy> selectBySceneNo(String sceneNo);
	
	public List<TPolicy> selectBySceneNoList(List<String> sceneNoList);
	
	public int selectCountBySceneNo(String sceneNo) throws Exception;
	
	public void updateStatus(long policyId, int status) throws Exception;
	
	public ResultCode delById(long PolicyId);
	
	public boolean update(TPolicy tPolicy);
	
	public boolean insert(TPolicy tPolicy) throws Exception;

	public boolean exists(String sceneNo, int weightValue, Long policyId, String name);

	public TPolicy selectById(long policyId);
}
