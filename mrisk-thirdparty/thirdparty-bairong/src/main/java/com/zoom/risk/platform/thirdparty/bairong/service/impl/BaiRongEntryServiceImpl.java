package com.zoom.risk.platform.thirdparty.bairong.service.impl;

import com.alibaba.fastjson.JSON;
import com.bfd.facade.MerchantServer;
import com.google.gson.reflect.TypeToken;
import com.zoom.risk.platform.thirdparty.bairong.service.BaiRongEntryService;
import com.zoom.risk.platform.thirdparty.common.service.ThreadLocalService;
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

@Service("baiRongEntryService")
public class BaiRongEntryServiceImpl implements BaiRongEntryService {
    private static final Logger logger = LogManager.getLogger(BaiRongEntryServiceImpl.class);

    private MerchantServer ms = new MerchantServer();

    @Value("${bairong.loginApiUrl}")
    private String loginApiUrl;
    @Value("${bairong.queryApiUrl}")
    private String queryApiUrl;
    @Value("${bairong.queryBadInfoUrl}")
    private String queryBadInfoUrl;

    @Value("${bairong.baiRongUser}")
    private String baiRongUser;
    @Value("${bairong.baiRongPwd}")
    private String baiRongPwd;
    @Value("${bairong.barRongCode}")
    private String barRongCode;

    @Resource(name="thirdPartyPoolExecutor")
    private ThreadPoolTaskExecutor thirdPartyPoolExecutor;

    @Resource(name="threadLocalService")
    private ThreadLocalService threadLocalService;

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
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("courtDiscreditType","-1");       //数据类型：失信被执行人信息
        resultMap.put("courtDiscreditStatus","-1");      //被执行人履行情况：全部未履行；部分未履行；全部已履行；失信记录已退出；null
        resultMap.put("courtExecutionType","-1");       //数据类型：最高法执行
        resultMap.put("courtExecutionStatus","-1");     //执行状态：执行中；已结案；null
        resultMap.put("personZT","-1"); //在逃 1：是，0：否
        resultMap.put("personWF","-1"); //违法行为 1：是，0：否
        resultMap.put("personSD","-1"); //涉毒 1：是，0：否
        resultMap.put("personXD","-1"); //吸毒 1：是，0：否
        String tokenId = this.login();
        if ( tokenId != null ){
            try {
                final String courtResultJson = this.query(tokenId, idCardNumber, userName, mobile);
                logger.info("Query parameters: [idCardNumber:{}, idCardNumber:{}, mobile:{}] result: [{}] ", idCardNumber,userName, mobile, courtResultJson);
                if ( courtResultJson != null ) {
                    Map<String, Object> tempMap = JSON.parseObject(courtResultJson, new TypeToken<HashMap<String,Object>>(){}.getType());
                    if ( "00".equals(tempMap.get("code"))){
                        if ( "1".equals(tempMap.get("flag_execution")) ){
                            resultMap.put("courtDiscreditType",tempMap.get("ex_bad1_datatype"));
                            resultMap.put("courtDiscreditStatus",tempMap.get("ex_bad1_performance"));
                            resultMap.put("courtExecutionType",tempMap.get("ex_execut1_datatype"));
                            resultMap.put("courtExecutionStatus",tempMap.get("ex_execut1_statute"));
                        }
                    }
                }
            }catch (Exception e){
                logger.error("", e);
            }
            try {
                final String badInfoResultJson = this.query(tokenId, idCardNumber, userName, mobile, "BadInfo",queryBadInfoUrl);
                logger.info("Query parameters: [idCardNumber:{}, idCardNumber:{}, mobile:{}] result: [{}] ", idCardNumber,userName,mobile,badInfoResultJson);
                if ( badInfoResultJson != null ) {
                    Map<String, Object> tempMap = JSON.parseObject(badInfoResultJson, new TypeToken<HashMap<String,Object>>(){}.getType());
                    if ( (tempMap.get("code")+"").equals("600000")){
                        Map<String, Object> flagMap = (Map)tempMap.get("flag");
                        if ( (flagMap.get("flag_badinfo")+"").equals("1") ){
                            Map<String, Object> productMap = (Map)tempMap.get("product");
                            resultMap.put("personZT",productMap.get("ztCheckresult"));
                            resultMap.put("personWF",productMap.get("wfxwCheckresult"));
                            resultMap.put("personSD",productMap.get("sdCheckresult"));
                            resultMap.put("personXD",productMap.get("xdCheckresult"));
                        }
                    }
                }
            }catch (Exception e){
                logger.error("", e);
            }
        }
        return resultMap;
    }

    private String query(String tokenId, String idCardNumber, String userName, String mobile) throws Exception {
        return query(tokenId, idCardNumber, userName, mobile, "Execution", queryApiUrl);
    }


    private String query(String tokenId, String idCardNumber, String userName, String mobile, String module, String apiUrl) throws Exception {
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
                result = null;
                if (code.equals("100007")) {
                    logger.error("Error!!! tokenid is empty!", result);
                }
            }
        }
        return result;
    }

    private String login() {
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
}
