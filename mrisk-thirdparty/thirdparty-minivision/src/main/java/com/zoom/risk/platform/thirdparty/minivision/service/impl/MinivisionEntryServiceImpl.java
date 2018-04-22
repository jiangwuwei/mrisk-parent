package com.zoom.risk.platform.thirdparty.minivision.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zoom.risk.platform.common.httpclient.HttpClientService;
import com.zoom.risk.platform.thirdparty.minivision.service.MinivisionEntryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */

@Service("minivisionEntryService")
public class MinivisionEntryServiceImpl implements MinivisionEntryService {
    private static final Logger logger = LogManager.getLogger(MinivisionEntryServiceImpl.class);
    private static final String blakList = "BlackListCheck";
    private static final String crimeInfo = "CrimeInfoCheck";

    @Value("${minivision.apiUrl}")
    private String apiUrl;

    @Value("${minivision.loginName}")
    private String loginName;

    @Value("${minivision.passWord}")
    private String password;

    @Resource(name="minivisionHttpClientService")
    private HttpClientService httpClientService;


    private static final String BlacklistHit = "blacklistHit";
    private static final String CrimeHit = "crimeHit";

    @Override
    public Map<String, Object> invoke(String idCardNumber, String name, String mobile) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put(BlacklistHit,"-1");
        resultMap.put(CrimeHit,"-1");

        try{
            String blackListResponse = this.sendBlackListRequest(idCardNumber,name,mobile);
            logger.info("BlackListService Response : {}",blackListResponse);
            Map<String,Object> blackListMap = (Map<String, Object>) JSON.parse(blackListResponse);
            String blackListResult = (String) blackListMap.get("RESULT");   //逾期黑名单状态码  1：命中  2：未命中 -1：异常情况
            if(!StringUtils.isEmpty(blackListResult)){
                resultMap.put(BlacklistHit,blackListResult);
            }
        }catch (Exception e){
            logger.error("",e);
        }

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

    private String sendBlackListRequest(String idCardNumber,String name, String mobile){
        return sendRequest(idCardNumber, name, mobile, blakList);
    }


    private String sendCrimeInfoRequest(String idCardNumber,String name){
        return sendRequest(idCardNumber, name, null, crimeInfo);
    }

    private String sendRequest(String idCardNumber,String name, String mobile, String serviceName){
        Map<String,Object> requestMap = new HashMap<>();
        Map<String,Object> paramMap = new HashMap<>();
        requestMap.put("loginName",loginName);
        requestMap.put("pwd",password);
        requestMap.put("serviceName",serviceName);
        if ( serviceName.equals(blakList)) {
            paramMap.put("mobile", mobile);
        }
        paramMap.put("idCard",idCardNumber);
        paramMap.put("name",name);
        requestMap.put("param",paramMap);
        JSONObject json = (JSONObject) JSON.toJSON(requestMap);
        String postUrl = apiUrl + serviceName;
        logger.info("Request url and parameters logger = [ url={},parameters={}]",postUrl, JSON.toJSONString(requestMap));
        return httpClientService.executeJsonPost(postUrl,json.toJSONString()).getResponse();
    }
}
