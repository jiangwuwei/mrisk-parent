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

@Service("minivisionCrimeService")
public class MinivisionCrimeEntryServiceImpl extends MinivisionAbstractService implements MinivisionEntryService {
    private static final Logger logger = LogManager.getLogger(MinivisionCrimeEntryServiceImpl.class);

    @Override
    public Map<String, Object> invoke(String idCardNumber, String name, String mobile) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put(CrimeHit,"-1");
        try {
            String crimeInfoResponse = this.sendCrimeInfoRequest(idCardNumber,name);
            logger.info("CrimeInfoService Response : {}",crimeInfoResponse);
            Map<String, Object> crimeInfoMap = (Map<String, Object>) JSON.parse(crimeInfoResponse);
            String crimeInfoResult = (String) crimeInfoMap.get("RESULT");   //违法行为状态码   1：命中   2：未命中 -1：系统异常
            if (!StringUtils.isEmpty(crimeInfoResult)) {
                resultMap.put(CrimeHit, crimeInfoResult);
            }
        }catch (Exception e){
            logger.error("",e);
        }
        return resultMap;
    }
}
