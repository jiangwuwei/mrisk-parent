package com.zoom.risk.platform.datacenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.datacenter.service.QuotaThirdPartyService;
import com.zoom.risk.platform.engine.vo.Quota;
import com.zoom.risk.platform.thirdparty.api.ThirdPartyApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by jiangyulin on 2015/12/2.
 */
@Service("quotaThirdPartyService")
public class QuotaThirdPartyServiceImpl implements QuotaThirdPartyService {
    private static final String SERVICENAME = "serviceName";

    @Resource(name="thirdPartyApi")
    private ThirdPartyApi thirdPartyApi;

    public Map<String, Object> getQuotaObject(Quota quota, Map<String,Object> riskInput){
        Map<String,Object> configMap = JSON.parseObject(quota.getQuotaContent(),  new TypeReference<Map<String, Object>>(){});
        String serviceName = String.valueOf(configMap.get(SERVICENAME));
        RpcResult<Map<String, Object>> result = thirdPartyApi.invoke(serviceName, riskInput);
        Map<String,Object> quotaValue = null;
        if ( result.isOK() ){
            quotaValue = result.getData();
        }
        return quotaValue;
    }

}
