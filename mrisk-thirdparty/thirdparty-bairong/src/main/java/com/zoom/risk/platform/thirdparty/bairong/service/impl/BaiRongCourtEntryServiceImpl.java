package com.zoom.risk.platform.thirdparty.bairong.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.zoom.risk.platform.thirdparty.bairong.service.BaiRongEntryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */

@Service("baiRongCourtService")
public class BaiRongCourtEntryServiceImpl extends BaiRongBaseServiceImpl implements BaiRongEntryService {
    private static final Logger logger = LogManager.getLogger(BaiRongCourtEntryServiceImpl.class);

    /**
     * 指标查询服务
     * @param idCardNumber
     * @param userName
     * @param mobile
     * @return
     */
    protected Map<String, Object> queryFromService(String idCardNumber, String userName, String mobile) {
        long time = System.currentTimeMillis();
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("courtDiscreditType","-1");       //数据类型：失信被执行人信息
        resultMap.put("courtDiscreditStatus","-1");      //被执行人履行情况：全部未履行；部分未履行；全部已履行；失信记录已退出；null
        resultMap.put("courtExecutionType","-1");       //数据类型：最高法执行
        resultMap.put("courtExecutionStatus","-1");     //执行状态：执行中；已结案；null
        String tokenId = this.login();
        if ( tokenId != null ){
            try {
                final String courtResultJson = this.query(tokenId, idCardNumber, userName, mobile,"Execution", queryApiUrl);
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
                super.saveThirdpartyLog(idCardNumber, userName, mobile, courtResultJson, (System.currentTimeMillis()-time) );
            }catch (Exception e){
                logger.error("", e);
            }
        }
        return resultMap;
    }
}
