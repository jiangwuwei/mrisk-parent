package com.zoom.risk.operating.decision.proxy.impl;

import com.zoom.risk.operating.common.db.DBSelector;
import com.zoom.risk.operating.common.po.ResultCode;
import com.zoom.risk.operating.decision.po.TPolicy;
import com.zoom.risk.operating.decision.proxy.DtPolicyServiceProxy;
import com.zoom.risk.operating.decision.service.DtPolicyService;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("dtPolicyServiceProxy")
public class DtPolicyServiceProxyImpl implements DtPolicyServiceProxy{

	private static final Logger logger = LogManager.getLogger(DtPolicyServiceProxyImpl.class);

	@Resource(name="sessionManager")
	private SessionManager sessionManager;
	@Resource(name="dtPolicyService")
	private DtPolicyService dtPolicyService;
	
	public List<TPolicy> selectBySceneNo(String sceneNo){
		return sessionManager.runWithSession( ()->{
			return dtPolicyService.selectBySceneNo(sceneNo);
		}, DBSelector.OPERATING_MASTER_DB);
	}
	
	public List<TPolicy> selectBySceneNoList(List<String> sceneNoList){
		return sessionManager.runWithSession( ()->{
			return dtPolicyService.selectBySceneNoList(sceneNoList);
		}, DBSelector.OPERATING_MASTER_DB);
	}
	
	public int selectCountBySceneNo(String sceneNo){
		return sessionManager.runWithSession( ()->{
			try {
				return dtPolicyService.selectCountBySceneNo(sceneNo);
			} catch (Exception e) {
				logger.error(e);
			}
			return -1;
		}, DBSelector.OPERATING_MASTER_DB);
	}
	
	public void updateStatus(long policyId, int status){
		sessionManager.runWithSession( ()->{
			try {
				dtPolicyService.updateStatus(policyId, status);
			} catch (Exception e) {
				logger.error(e);
			}
		}, DBSelector.OPERATING_MASTER_DB);
	}
	
	public ResultCode delById(long policyId){
		return sessionManager.runWithSession( ()->{
			return dtPolicyService.delById(policyId);
		}, DBSelector.OPERATING_MASTER_DB);
	}
	
	public boolean update(TPolicy tPolicy){
		return sessionManager.runWithSession( ()->{
			return dtPolicyService.update(tPolicy);
		}, DBSelector.OPERATING_MASTER_DB);
	}
	
	public boolean insert(TPolicy tPolicy){
		return sessionManager.runWithSession( ()->{
			try {
				return dtPolicyService.insert(tPolicy);
			} catch (Exception e) {
				logger.error(e);
			}
			return false;
		}, DBSelector.OPERATING_MASTER_DB);
	}

    @Override
    public boolean exists(String sceneNo, int weightValue, Long policyId, String name) {
        return sessionManager.runWithSession( ()->{
            return dtPolicyService.exists(sceneNo, weightValue, policyId, name);
        }, DBSelector.OPERATING_MASTER_DB);
    }

    @Override
    public TPolicy selectById(long policyId) {
        return sessionManager.runWithSession( ()->{
            return dtPolicyService.selectById(policyId);
        }, DBSelector.OPERATING_MASTER_DB);
    }
}
