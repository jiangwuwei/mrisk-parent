package com.zoom.risk.platform.thirdparty.minivision.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.platform.thirdparty.minivision.service.MinivisionEntryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */

@Service("minivisionBlacklistService")
public class MinivisionBlacklistEntryServiceImpl extends MinivisionAbstractService implements MinivisionEntryService {
    private static final Logger logger = LogManager.getLogger(MinivisionBlacklistEntryServiceImpl.class);

    @Override
    public Map<String, Object> invoke(String idCardNumber, String name, String mobile) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put(BlacklistHit,"-1");
        try{
            long time = System.currentTimeMillis();
            String blackListResponse = this.sendBlackListRequest(idCardNumber,name,mobile);
            logger.info("BlackListService Response : {}",blackListResponse);
            Map<String,Object> blackListMap = (Map<String, Object>) JSON.parse(blackListResponse);
            String blackListResult = (String) blackListMap.get("RESULT");   //逾期黑名单状态码  1：命中  2：未命中 -1：异常情况
            if(!StringUtils.isEmpty(blackListResult)){
                resultMap.put(BlacklistHit,blackListResult);
            }
            super.saveThirdpartyLog(idCardNumber,name,mobile,JSON.toJSONString(resultMap),System.currentTimeMillis()-time);
        }catch (Exception e){
            logger.error("",e);
        }
        return resultMap;
    }
}
