package com.zoom.risk.platform.thirdparty.facade.adapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.ctr.util.LsManager;
import com.zoom.risk.platform.thirdparty.bairong.service.BaiRongEntryService;
import com.zoom.risk.platform.thirdparty.common.annotation.Invoker;
import com.zoom.risk.platform.thirdparty.common.service.InvokerService;
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
@Invoker(value="baiRongThirdpartyAdapter",serviceName = "baiRongCrimeEntryService")
public class BaiRongCrimeAdapter implements InvokerService {
    private static final Logger logger = LogManager.getLogger(BaiRongCrimeAdapter.class);

    @Resource(name="baiRongCrimeService")
    private BaiRongEntryService baiRongCrimeService;

    @Value("${useMock}")
    private boolean useMock;

    @Override
    public RpcResult<Map<String, Object>> invoke(Map<String, Object> riskInput) {
        LsManager.getInstance().check();
        RpcResult<Map<String, Object>> rpcResult = new RpcResult();
        String mobile = String.valueOf(riskInput.get("mobile"));
        String userName = String.valueOf(riskInput.get("userName"));
        String idCardNumber = String.valueOf(riskInput.get("idCardNumber"));
        BaiRongEntryService realEntryService = baiRongCrimeService;
        if(useMock){
            rpcResult.setData(mockMap());
        }else {
            try {
                Map<String, Object> result = realEntryService.invoke(idCardNumber, userName, mobile);
                rpcResult.setData(result);
            } catch (Exception e) {
                rpcResult.setErrorCode(RpcResult.ERROR_SERVER_500);
                logger.error("", e);
            }
        }
        logger.info("BaiRongThirdpartyAdapter's result Map : {}", JSON.toJSONString(rpcResult));
        return rpcResult;
    }

    private Map<String,Object> mockMap(){
        String crimeInfo = "{ \"personZT\": \"1\", \"personXD\": \"0\", \"personSD\": \"1\", \"personWF\": \"1\" }";
        return JSON.parseObject(crimeInfo,new TypeReference<HashMap<String,Object>>(){});
    }
}
