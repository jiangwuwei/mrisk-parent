package com.zoom.risk.platform.thirdparty.dbservice.impl;

import com.zoom.risk.platform.thirdparty.dbservice.ThirdPartyDbService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("thirdPartyDbService")
public class ThirdPartyDbServiceImpl implements ThirdPartyDbService {
    private static final Logger logger = LogManager.getLogger(ThirdPartyDbServiceImpl.class);

    private static final String SaveThirdpartyLogSQL = "insert into zoom_risk_thirdparty_log(service_name,scene,risk_id,request_params,response_body,taking_time) values(?,?,?,?,?,?)";

    @Resource(name= "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(value ="transactionManager", readOnly = false)
    public void saveThirdpartyLog(String serviceName,String scene, String riskId, String requestParams, String responseBody, long takingTime) {
        logger.info("Save to db [serviceName:{},scene:{},riskId:{},requestParams:{},responseBody:{},takingTime:{}]",serviceName,scene,riskId,requestParams,responseBody,takingTime);
        jdbcTemplate.update(SaveThirdpartyLogSQL,serviceName,scene,riskId,requestParams,responseBody,takingTime);
    }
}
