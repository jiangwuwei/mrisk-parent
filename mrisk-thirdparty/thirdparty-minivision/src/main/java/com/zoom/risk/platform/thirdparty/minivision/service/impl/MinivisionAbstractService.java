package com.zoom.risk.platform.thirdparty.minivision.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zoom.risk.platform.common.httpclient.HttpClientService;
import com.zoom.risk.platform.thirdparty.common.service.ThreadLocalService;
import com.zoom.risk.platform.thirdparty.dbservice.ThirdPartyDbService;
import com.zoom.risk.platform.thirdparty.minivision.service.MinivisionEntryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */
@Service("minivisionAbstractService")
public class MinivisionAbstractService implements MinivisionEntryService {
    private static final Logger logger = LogManager.getLogger(MinivisionAbstractService.class);
    public static final String blakList = "BlackListCheck";
    public static final String crimeInfo = "CrimeInfoCheck";

    @Value("${minivision.apiUrl}")
    private String apiUrl;

    @Value("${minivision.loginName}")
    private String loginName;

    @Value("${minivision.passWord}")
    private String password;

    @Resource(name="minivisionHttpClientService")
    private HttpClientService httpClientService;

    @Resource(name="thirdPartyPoolExecutor")
    protected ThreadPoolTaskExecutor thirdPartyPoolExecutor;

    @Resource(name="threadLocalService")
    protected ThreadLocalService threadLocalService;

    @Resource(name="thirdPartyDbService")
    private ThirdPartyDbService thirdPartyDbService;

    public static final String BlacklistHit = "blacklistHit";

    public static final String CrimeHit = "crimeHit";


    public String sendBlackListRequest(String idCardNumber,String name, String mobile){
        return sendRequest(idCardNumber, name, mobile, blakList);
    }


    public String sendCrimeInfoRequest(String idCardNumber,String name){
        return sendRequest(idCardNumber, name, null, crimeInfo);
    }

    public String sendRequest(String idCardNumber,String name, String mobile, String serviceName){
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

    @Override
    public Map<String, Object> invoke(String idCardNumber, String name, String mobile) {
        return null;
    }

    protected void saveThirdpartyLog(String idCardNumber, String userName, String mobile, String responseJson ,long takingTime){
        final String riskId = threadLocalService.getRiskId();
        final String serviceName = threadLocalService.getServiceName();
        final String scene = threadLocalService.getScene();
        thirdPartyPoolExecutor.submit(()->{
            Map<String, String> requestMap = new HashMap<>();
            requestMap.put("idCardNumber",idCardNumber);
            requestMap.put("mobile",mobile);
            requestMap.put("userName",userName);
            String requestJson = JSON.toJSONString(requestMap);
            thirdPartyDbService.saveThirdpartyLog(serviceName,scene,riskId,requestJson,responseJson,takingTime);
        });
    }



}
