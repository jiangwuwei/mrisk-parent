package com.zoom.risk.platform.thirdparty.facade.adapter;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.ctr.util.LsManager;
import com.zoom.risk.platform.thirdparty.common.annotation.Invoker;
import com.zoom.risk.platform.thirdparty.common.service.InvokerService;
import com.zoom.risk.platform.thirdparty.minivision.service.MinivisionEntryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyulin
 *Oct 26, 2015
 */
@Invoker(value="minivisionCrimeAdapter",serviceName = "minivisionCrimeEntryService")
public class MinivisionCrimeAdapter implements InvokerService {
    private static final Logger logger = LogManager.getLogger(MinivisionCrimeAdapter.class);

    @Resource(name="minivisionCrimeService")
    private MinivisionEntryService minivisionCrimeService;
    @Value("${useMock}")
    private boolean useMock;

    @Override
    public RpcResult<Map<String, Object>> invoke(Map<String, Object> riskInput) {
        LsManager.getInstance().check();
        RpcResult<Map<String, Object>> rpcResult = new RpcResult();
        String mobile = String.valueOf(riskInput.get("mobile"));
        String userName = String.valueOf(riskInput.get("userName"));
        String idCardNumber = String.valueOf(riskInput.get("idCardNumber"));
        if(useMock){
            rpcResult.setData(invokeMock());
        }else {
            try {
                Map<String, Object> result = minivisionCrimeService.invoke(idCardNumber, userName, mobile);
                rpcResult.setData(result);
            } catch (Exception e) {
                rpcResult.setErrorCode(RpcResult.ERROR_SERVER_500);
                logger.error("", e);
            }
        }
        logger.info("MinivisionCrimeAdapter's result Map : {}", JSON.toJSONString(rpcResult));
        return rpcResult;
    }

    private Map<String, Object> invokeMock() {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("crimeHit","1");
        return resultMap;
    }
}
