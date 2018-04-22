package com.zoom.risk.platform.thirdparty.facade.api.impl;


import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.thirdparty.api.ThirdPartyApi;
import com.zoom.risk.platform.thirdparty.facade.service.ThirdPartyFacadeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author jiangyulin
 *Oct 26, 2015
 */
@Service("thirdPartyApi")
public class ThirdPartyApiImpl implements ThirdPartyApi {

    @Resource(name="thirdPartyFacadeService")
    private ThirdPartyFacadeService thirdPartyFacadeService;

    @Override
    public RpcResult<Map<String, Object>> invoke(String serviceName, Map<String, Object> riskInput) {
        return thirdPartyFacadeService.invoke(serviceName, riskInput);
    }
}
