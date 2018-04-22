package com.zoom.risk.platform.decision.service.impl;


import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.decision.po.TQuota;
import com.zoom.risk.platform.decision.service.QuotaQueryHelpService;
import com.zoom.risk.platform.thirdparty.api.ThirdPartyApi;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by jiangyulin on 2017/5/24.
 */
@Service("quotaQueryHelpService")
public class QuotaQueryHelpServiceImpl implements QuotaQueryHelpService {
    private static final Logger logger = LogManager.getLogger(DTreeEngineApiImpl.class);
    private static final String SERVICENAME = "serviceName";

    @Resource(name="thirdPartyApi")
    private ThirdPartyApi thirdPartyApi;

    @Override
    public Object getQuotaValue(TQuota quota, Map<String, Object> riskInput) {
        Object value = null;
        String serviceName = quota.getParamName();
        try{
            RpcResult<Map<String, Object>> result = thirdPartyApi.invoke(serviceName, riskInput);
            if ( result.isOK() ){
                Map<String,Object> map = result.getData();
                if ( map.containsKey("quotaKey") ){
                    value = map.get("quotaKey");
                }else{
                    value = map;
                }
                logger.info(" quota value [{}], params [{}] ", value, value );
            }else{
                logger.error("getting quota value happens error, errorMessage : " +  result.getMessage() );
            }
        }catch (Throwable e){
            logger.error("", e);
        }
        if ( value == null ) {
            if( quota.getDataType() == TQuota.DATA_TYPE_1 ){
                value = -999;
            }else if (quota.getDataType() == TQuota.DATA_TYPE_3 ){
                value = -999;
            }
        }
        return value;
    }

}
