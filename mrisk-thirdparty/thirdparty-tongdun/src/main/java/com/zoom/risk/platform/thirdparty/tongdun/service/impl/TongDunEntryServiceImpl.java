package com.zoom.risk.platform.thirdparty.tongdun.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.reflect.TypeToken;
import com.zoom.risk.platform.common.httpclient.HttpClientService;
import com.zoom.risk.platform.common.httpclient.HttpResponseWapper;
import com.zoom.risk.platform.thirdparty.common.service.ThreadLocalService;
import com.zoom.risk.platform.thirdparty.common.utils.ThirdPartyConstants;
import com.zoom.risk.platform.thirdparty.dbservice.ThirdPartyDbService;
import com.zoom.risk.platform.thirdparty.tongdun.service.TongDunEntryService;
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
 *Oct 26, 2015
 */
@Service("tongDunEntryService")
public class TongDunEntryServiceImpl implements TongDunEntryService {
    private static final Logger logger = LogManager.getLogger(TongDunEntryServiceImpl.class);

    @Value("${tongdun.apiUrl}")
    private String apiUrl;
    @Value("${tongdun.partnerCode}")
    private String partnerCode;
    @Value("${tongdun.appName}")
    private String appName;
    @Value("${tongdun.partnerKey}")
    private String partnerKey;
    @Resource(name="tongdunHttpClientService")
    private HttpClientService httpClientService;
    @Resource(name="thirdPartyDbService")
    private ThirdPartyDbService thirdPartyDbService;
    @Resource(name="threadLocalService")
    private ThreadLocalService threadLocalService;
    @Resource(name="thirdPartyPoolExecutor")
    private ThreadPoolTaskExecutor thirdPartyPoolExecutor;

    public Map<String, Object> invoke(String idCardNumber, String accountName, String accountMobile ) {
        long time = System.currentTimeMillis();
        HttpResponseWapper<String> responseWapper = sendRequest(idCardNumber,accountName,accountMobile);
        String response = responseWapper.getResponse();
        Integer score = this.handleResponse(responseWapper) ;
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(ThirdPartyConstants.QUOTA_KEY, score);
        this.saveThirdpartyLog(idCardNumber,accountName,accountMobile,response,System.currentTimeMillis()-time);
        return resultMap;
    }

    private Integer handleResponse(HttpResponseWapper<String> response){
        if ( response.getResultCode() != HttpResponseWapper.SC_OK ){
            throw new RuntimeException("请求同盾Http服务发生错误, response :" + response.getErrorMessage());
        }
        logger.info("Response : {}", response.getResponse() );
        Map<String,Object> fingerprintMap = new HashMap();
        Map<String,Object> resultMap = JSON.parseObject(response.getResponse(), new TypeToken<HashMap<String,Object>>(){}.getType());
        boolean isSuccess = (boolean) resultMap.get("success");
        Integer score = null;
        if( isSuccess ){
            JSONObject jsonObject = (JSONObject)resultMap.get("result_desc");
            Map<String,Object> map = (Map<String,Object>)jsonObject.get("ANTIFRAUD");
            score = (Integer)map.get("score");
        }
        return score;
    }


    private HttpResponseWapper<String> sendRequest(String idCardNumber, String accountName, String accountMobile){
        Map<String,Object> paramMap = new HashMap<>();
        String postUrl = this.apiUrl + String.format("?%s=%s&%s=%s&%s=%s", PARTNER_CODE, partnerCode,APP_NAME, appName, PARTNER_KEY,partnerKey);
        paramMap.put("account_mobile", accountMobile);
        paramMap.put("account_name", accountName);
        paramMap.put("id_number", idCardNumber);
        logger.info("Request url and parameters logger = [ url={}, parameters={}]", postUrl, JSON.toJSONString(paramMap));
        return httpClientService.executePost(postUrl, paramMap);
    }

    private void saveThirdpartyLog(String idCardNumber, String userName, String mobile, String responseJson ,long takingTime){
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
