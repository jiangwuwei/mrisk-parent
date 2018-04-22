package com.zoom.risk.platform.thirdparty.facade.adapter;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.ctr.util.LsManager;
import com.zoom.risk.platform.thirdparty.common.annotation.Invoker;
import com.zoom.risk.platform.thirdparty.common.service.InvokerService;
import com.zoom.risk.platform.thirdparty.tongdun.service.TongDunEntryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author jiangyulin
 *Oct 26, 2015
 */
@Invoker(value="tongDunThirdpartyAdapter",serviceName = "tongDunEntryService")
public class TongDunThirdpartyAdapter implements InvokerService {
    private static final Logger logger = LogManager.getLogger(TongDunThirdpartyAdapter.class);
    @Resource(name="tongDunEntryService")
    private TongDunEntryService tongDunEntryService;
    @Resource(name="tongDunMockEntryService")
    private TongDunEntryService tongDunMockEntryService;
    @Value("${useMock}")
    private boolean useMock;

    @Override
    public RpcResult<Map<String, Object>> invoke(Map<String, Object> riskInput) {
        LsManager.getInstance().check();
        RpcResult<Map<String, Object>> rpcResult = new RpcResult();
        String mobile = String.valueOf(riskInput.get("mobile"));
        String userName = String.valueOf(riskInput.get("userName"));
        String idCardNumber = String.valueOf(riskInput.get("idCardNumber"));
        TongDunEntryService realEntryService = tongDunEntryService;
        if(useMock){
            realEntryService = tongDunMockEntryService;
        }
        try {
            Map<String, Object> result = realEntryService.invoke( idCardNumber, userName, mobile );
            rpcResult.setData(result);
        }catch (Exception e){
            rpcResult.setErrorCode(RpcResult.ERROR_SERVER_500);
           logger.error("", e);
        }
        logger.info("TongDunThirdpartyAdapter's result Map : {}", JSON.toJSONString(rpcResult));
        return rpcResult;
    }
}
