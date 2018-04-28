package com.zoom.risk.platform.thirdparty.bairong.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.zoom.risk.platform.thirdparty.bairong.service.BaiRongEntryService;
import net.sf.json.JSONObject;
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

@Service("baiRongEntryService")
public class BaiRongCrimeServiceImpl extends BaiRongBaseServiceImpl implements BaiRongEntryService{
    private static final Logger logger = LogManager.getLogger(BaiRongCrimeServiceImpl.class);

    /**
     * 指标查询服务
     * @param idCardNumber
     * @param userName
     * @param mobile
     * @return
     */
    protected Map<String, Object> queryFromService(String idCardNumber, String userName, String mobile) {
        long time  = System.currentTimeMillis();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("personZT","-1"); //在逃 1：是，0：否
        resultMap.put("personWF","-1"); //违法行为 1：是，0：否
        resultMap.put("personSD","-1"); //涉毒 1：是，0：否
        resultMap.put("personXD","-1"); //吸毒 1：是，0：否
        String tokenId = this.login();
        if ( tokenId != null ){
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
                super.saveThirdpartyLog(idCardNumber, userName, mobile, badInfoResultJson,  System.currentTimeMillis()-time );
            }catch (Exception e){
                logger.error("", e);
            }
        }
        return resultMap;
    }
}
