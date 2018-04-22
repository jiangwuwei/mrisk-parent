package com.zoom.risk.platform.thirdparty.api;


import com.zoom.risk.platform.common.rpc.RpcResult;

import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */
public interface ThirdPartyApi {

    public RpcResult<Map<String, Object>> invoke(String serviceName, Map<String, Object> riskInput);

}
