package com.zoom.risk.platform.api;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.platform.common.rpc.RpcResult;
import com.zoom.risk.platform.thirdparty.api.ThirdPartyApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration(locations={"classpath*:spring/spring-config-*.xml"})
public class ThirdPartyApiServiceTest {

    @Resource(name="thirdPartyApi")
    private ThirdPartyApi thirdPartyApi;

    @Test
    public void testTongDun(){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("mobile","13582466569");
        paramMap.put("userName","王永林");
        paramMap.put("idCardNumber","131024198403102018");
        RpcResult<Map<String,Object>> resultMap = thirdPartyApi.invoke("tongDunEntryService",paramMap);
        System.out.println("***\r****\r*****\rresultMap = " + JSON.toJSONString(resultMap));
    }

    @Test
    public void testBaiRong(){
        Map<String,Object> paramMap = new HashMap();
        paramMap.put("mobile","13986671110");
        paramMap.put("userName","王亮");
        paramMap.put("idCardNumber","140502198811102244");
        RpcResult<Map<String,Object>> resultMap = thirdPartyApi.invoke("baiRongEntryService",paramMap);
        System.out.println("***\r****\r*****\rresultMap = " + JSON.toJSONString(resultMap));
    }
}
