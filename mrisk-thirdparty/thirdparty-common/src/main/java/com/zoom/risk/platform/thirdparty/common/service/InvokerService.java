package com.zoom.risk.platform.thirdparty.common.service;


import com.zoom.risk.platform.common.rpc.RpcResult;

import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */
public interface InvokerService {

    RpcResult<Map<String, Object>> invoke(Map<String,Object> riskParams);

}
