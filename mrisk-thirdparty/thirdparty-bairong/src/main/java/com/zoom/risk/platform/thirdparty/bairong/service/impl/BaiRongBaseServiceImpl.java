package com.zoom.risk.platform.thirdparty.bairong.service.impl;

import com.alibaba.fastjson.JSON;
import com.bfd.facade.MerchantServer;
import com.zoom.risk.platform.thirdparty.bairong.service.BaiRongEntryService;
import com.zoom.risk.platform.thirdparty.common.service.ThreadLocalService;
import com.zoom.risk.platform.thirdparty.dbservice.ThirdPartyDbService;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */

@Service("baiRongBaseEntryService")
public class BaiRongBaseServiceImpl implements BaiRongEntryService {
    private static final Logger logger = LogManager.getLogger(BaiRongBaseServiceImpl.class);

    protected MerchantServer ms = new MerchantServer();

    @Value("${bairong.loginApiUrl}")
    protected String loginApiUrl;
    @Value("${bairong.queryApiUrl}")
    protected String queryApiUrl;
    @Value("${bairong.queryBadInfoUrl}")
    protected String queryBadInfoUrl;

    @Value("${bairong.baiRongUser}")
    protected String baiRongUser;
    @Value("${bairong.baiRongPwd}")
    protected String baiRongPwd;
    @Value("${bairong.barRongCode}")
    protected String barRongCode;

    @Resource(name="thirdPartyPoolExecutor")
    protected ThreadPoolTaskExecutor thirdPartyPoolExecutor;

    @Resource(name="threadLocalService")
    protected ThreadLocalService threadLocalService;

    @Resource(name="thirdPartyDbService")
    private ThirdPartyDbService thirdPartyDbService;

    @Override
    public Map<String, Object> invoke(String idCardNumber, String userName, String mobile) {
        return this.queryFromService(idCardNumber, userName, mobile);
    }

    /**
     * 指标查询服务
     * @param idCardNumber
     * @param userName
     * @param mobile
     * @return
     */
    protected Map<String, Object> queryFromService(String idCardNumber, String userName, String mobile) {
        return null;
    }


    protected String query(String tokenId, String idCardNumber, String userName, String mobile, String module, String apiUrl) throws Exception {
        JSONObject json = new JSONObject();
        JSONObject reqData = new JSONObject();
        json.put("apiName", apiUrl);//config配置文件对应的url地址。
        json.put("tokenid", tokenId);
        reqData.put("meal", module);//模块名字，填写子产品代号。必填
        reqData.put("id", idCardNumber);
        reqData.put("cell", mobile);
        reqData.put("name",userName);
        json.put("reqData", reqData);
        String result = ms.getApiData(json.toString(),barRongCode);
        String code =  "";
        if (result.contains("code")){
            code =  JSONObject.fromObject(result).getString("code");
            if ( !code.equals("00") && !code.equals("600000")) {
                if (code.equals("100007")) {
                    logger.error("Error!!! tokenid is empty!", result);
                }
            }
        }
        return result;
    }

    protected String login() {
        String tokenId = null;
        try {
            String loginResult = ms.login(baiRongUser, baiRongPwd, loginApiUrl,barRongCode);
            if( !StringUtils.isEmpty(loginResult)){
                JSONObject json = JSONObject.fromObject(loginResult);
                if(json.containsKey("tokenid")){
                    tokenId = json.getString("tokenid");
                }
            }
        } catch (Exception e) {
            logger.error("Getting tokenid happens error,",e);
        }
        return tokenId;
    }

    protected void saveThirdpartyLog(String idCardNumber, String userName, String mobile, String responseJson, long takingTime){
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
