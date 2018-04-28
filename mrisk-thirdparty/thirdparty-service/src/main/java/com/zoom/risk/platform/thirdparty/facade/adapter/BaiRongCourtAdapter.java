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
@Invoker(value="baiRongCourtAdapter",serviceName = "baiRongCourtEntryService")
public class BaiRongCourtAdapter implements InvokerService {
    private static final Logger logger = LogManager.getLogger(BaiRongCourtAdapter.class);

    @Resource(name="baiRongCourtService")
    private BaiRongEntryService baiRongCourtService;

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
            rpcResult.setData(mockMap());
        }else {
            try {
                Map<String, Object> result = baiRongCourtService.invoke(idCardNumber, userName, mobile);
                rpcResult.setData(result);
            } catch (Exception e) {
                rpcResult.setErrorCode(RpcResult.ERROR_SERVER_500);
                logger.error("", e);
            }
        }
        logger.info("BaiRongCourtAdapter's result Map : {}", JSON.toJSONString(rpcResult));
        return rpcResult;
    }

    private Map<String,Object> mockMap(){
        String courtInfo = "{ \"courtExecutionStatus\": \"执行中\", \"courtDiscreditStatus\": \"全部未履行\", \"courtExecutionType\": \"最高法执行\", \"courtDiscreditType\": \"失信被执行人\" }";
        return JSON.parseObject(courtInfo,new TypeReference<HashMap<String,Object>>(){});
    }
}
