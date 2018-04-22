package com.zoom.risk.operating.scard.service;

import com.zoom.risk.operating.ruleconfig.common.Constants;
import com.zoom.risk.operating.ruleconfig.common.ResultCode;
import com.zoom.risk.operating.ruleconfig.model.RiskNumber;
import com.zoom.risk.operating.ruleconfig.service.RiskNumberService;
import com.zoom.risk.operating.scard.dao.SCardRouterMapper;
import com.zoom.risk.operating.scard.model.SCardRouter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
@Service("scardRouterService")
public class SCardRouterService {

    private static final Logger logger = LogManager.getLogger(SCardRouterService.class);

    @Autowired
    private SCardRouterMapper scardRouterMapper;
    @Autowired
    private RiskNumberService riskNumberService;

    public SCardRouter selectById(long policyRouterId) {
        try {
            return scardRouterMapper.selectByPrimaryKey(policyRouterId);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public ResultCode delById(long policyRouterId) {
        try {
            scardRouterMapper.deleteByPrimaryKey(policyRouterId);
        } catch (Exception e) {
            logger.error(e);
            return ResultCode.DB_ERROR;
        }
        return ResultCode.SUCCESS;
    }

    public void deleteByPolicyId(long policyId) throws Exception {
        scardRouterMapper.deleteByPolicyId(policyId);
    }

    @Transactional(rollbackFor = Exception.class)
    public void insert(SCardRouter policyRouter) {
        policyRouter.setRouterNo(riskNumberService.getJYLRiskNumber(RiskNumber.SCARD_CLASS_ROUTER,policyRouter.getSceneNo()));
        policyRouter.setStatus(Constants.STATUS_IN_EFFECT);
        scardRouterMapper.insert(policyRouter);
    }

    public List<SCardRouter> selectBySceneNo(String sceneNo) {
        try {
            return scardRouterMapper.selectBySceneNo(sceneNo);
        } catch (Exception e) {
            logger.error(e);
        }
        return Collections.EMPTY_LIST;
    }

    public boolean updateById(SCardRouter policyRouter) {
        int ret = scardRouterMapper.updateByPrimaryKey(policyRouter);
        return ret == 1 ? true : false;
    }

    public List<String> selectDistinctSceneNo() {
        try {
            return scardRouterMapper.selectDistinctSceneNo();
        } catch (Exception e) {
            logger.error(e);
        }
        return Collections.EMPTY_LIST;
    }

    public boolean existPolicy(Long policyId) {
        try {
            Integer count = scardRouterMapper.existPolicy(policyId);
            return (count == null || count.intValue() == 0) ? false : true;
        } catch (Exception e) {
            logger.error(e);
        }
        return false;
    }
}
