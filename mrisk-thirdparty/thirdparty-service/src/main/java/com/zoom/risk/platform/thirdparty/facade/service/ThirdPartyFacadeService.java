package com.zoom.risk.platform.thirdparty.facade.service;


import com.zoom.risk.platform.common.rpc.RpcResult;

import java.util.Map;

/**
 * @author jiangyulin
 *Oct 26, 2015
 */
public interface ThirdPartyFacadeService {

    public RpcResult<Map<String, Object>> invoke (String serviceName, Map<String, Object> riskInput);
}
