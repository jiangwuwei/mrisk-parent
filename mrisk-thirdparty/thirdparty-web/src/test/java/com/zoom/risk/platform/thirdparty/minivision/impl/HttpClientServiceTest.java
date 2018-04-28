package com.zoom.risk.platform.thirdparty.minivision.impl;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.platform.common.httpclient.HttpClientService;
import com.zoom.risk.platform.common.httpclient.HttpResponseWapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration(locations={"classpath*:spring/spring-config-*.xml"})
public class HttpClientServiceTest {
    private static final String PRO_URL = "https://www.miniscores.cn:8313/CreditFunc/v2.1/";
    private static final String TEST_URL = "https://114.55.36.16:8090/CreditFunc/v2.1/";

    @Resource(name="minivisionHttpClientService")
    private HttpClientService httpClientService;

    @Test
    public void testBlackListCheck(){
        Map<String,Object> requestMap = new HashMap<>();
        Map<String ,Object> paramMap = new HashMap<>();
        requestMap.put("loginName","test9");
        requestMap.put("pwd","123456");
        requestMap.put("serviceName","BlackListCheck");
        paramMap.put("idCard","142702199702014226");
        paramMap.put("name","张三");
        paramMap.put("mobile","15572098371");
        requestMap.put("param",paramMap);
        HttpResponseWapper<String> response = httpClientService.executeJsonPost(TEST_URL + "BlackListCheck",JSON.toJSONString(requestMap));
        System.out.println(response.getResponse());
    }

    @Test
    public void testCrimeInfoCheck(){
        Map<String,Object> requestMap = new HashMap<>();
        Map<String ,Object> paramMap = new HashMap<>();
        requestMap.put("loginName","test9");
        requestMap.put("pwd","123456");
        requestMap.put("serviceName","CrimeInfoCheck");
        paramMap.put("idCard","142702199702014226");
        paramMap.put("name","张三");
        requestMap.put("param",paramMap);
        HttpResponseWapper<String> response = httpClientService.executeJsonPost(TEST_URL + "CrimeInfoCheck",JSON.toJSONString(requestMap));
        System.out.println(response.getResponse());
    }
}
