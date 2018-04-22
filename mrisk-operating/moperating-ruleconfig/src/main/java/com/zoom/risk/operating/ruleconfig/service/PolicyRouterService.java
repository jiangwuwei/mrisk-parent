package com.zoom.risk.operating.ruleconfig.service;

import com.zoom.risk.operating.ruleconfig.common.Constants;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.ruleconfig.dao.PolicyRouterMapper;
import com.zoom.risk.operating.ruleconfig.model.PolicyRouter;
import com.zoom.risk.operating.ruleconfig.model.RiskNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service("policyRouterService")
public class PolicyRouterService {

    private static final Logger logger = LogManager.getLogger(PolicyRouterService.class);

    @Autowired
    private PolicyRouterMapper policyRouterMapper;
    @Autowired
    private RiskNumberService riskNumberService;

    public PolicyRouter selectById(long policyRouterId) {
        try {
            return policyRouterMapper.selectByPrimaryKey(policyRouterId);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public ResultCode delById(long policyRouterId) {
        try {
            policyRouterMapper.deleteByPrimaryKey(policyRouterId);
        } catch (Exception e) {
            logger.error(e);
            return ResultCode.DB_ERROR;
        }
        return ResultCode.SUCCESS;
    }

    public void deleteByPolicyId(long policyId) throws Exception {
        policyRouterMapper.deleteByPolicyId(policyId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insert(PolicyRouter policyRouter) {
        int seqNo = riskNumberService.selectSeqNo(RiskNumber.ENTITY_CLASS_ROUTER, policyRouter.getSceneNo());
        //get new quota number
        policyRouter.setRouterNo(riskNumberService.getRiskNumber(seqNo + 1, policyRouter.getSceneNo(), RiskNumber.ENTITY_CLASS_ROUTER));
        policyRouter.setStatus(Constants.STATUS_IN_EFFECT);
        //insert or update quota seq no
        riskNumberService.insertOrUpdate(RiskNumber.ENTITY_CLASS_ROUTER, policyRouter.getSceneNo(), seqNo + 1);
        policyRouterMapper.insert(policyRouter);
    }

    public List<PolicyRouter> selectBySceneNo(String sceneNo) {
        try {
            return policyRouterMapper.selectBySceneNo(sceneNo);
        } catch (Exception e) {
            logger.error(e);
        }
        return Collections.EMPTY_LIST;
    }

    public boolean updateById(PolicyRouter policyRouter) {
        int ret = policyRouterMapper.updateByPrimaryKey(policyRouter);
        return ret == 1 ? true : false;
    }

    public List<String> selectDistinctSceneNo() {
        try {
            return policyRouterMapper.selectDistinctSceneNo();
        } catch (Exception e) {
            logger.error(e);
        }
        return Collections.EMPTY_LIST;
    }

    public boolean existPolicy(Long policyId) {
        try {
            Integer count = policyRouterMapper.existPolicy(policyId);
            return (count == null || count.intValue() == 0) ? false : true;
        } catch (Exception e) {
            logger.error(e);
        }
        return false;
    }
}
