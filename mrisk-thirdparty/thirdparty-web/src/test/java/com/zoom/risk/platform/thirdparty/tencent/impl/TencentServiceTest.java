package com.zoom.risk.platform.thirdparty.tencent.impl;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.platform.common.httpclient.HttpClientService;
import com.zoom.risk.platform.common.httpclient.HttpResponseWapper;
import com.zoom.risk.platform.thirdparty.tencent.utils.AESPlus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration(locations={"classpath*:spring/spring-config-*.xml"})
public class TencentServiceTest {
    private static final String REQUEST_NO_URL = "https://credit.xinyong.com.cn/fcgi-bin/zx_get_seqno.fcgi";
    private static final String REQUEST_CREDIT_URL = "https://credit.xinyong.com.cn/fcgi-bin/zx_get_score.fcgi";
    private static final String KEY = "474609982708c367";
    private static final String ent_no = "10001106";

    @Resource(name="minivisionHttpClientService")
    private HttpClientService httpClientService;

    @Test
    public void test() throws Exception {
        String idCardNumber="622722199111261452";
        String name= "张宝合";
        String code = "uin=" + idCardNumber + "&time="+ System.currentTimeMillis()/1000;
        String result =  null;
        result = AESPlus.getStr(AESPlus.encrypt(KEY, code));
        Map<String,Object> params = new HashMap<>();
        params.put("ent_no",ent_no);
        params.put("req_data",result);
        HttpResponseWapper<String> responseWapper = httpClientService.executeGet(REQUEST_NO_URL, params);
        if ( responseWapper.getResultCode() == HttpResponseWapper.SC_OK ){
            String body = responseWapper.getResponse();
            Map<String,Object> resultMap = JSON.parseObject(body,HashMap.class);
            if ( "0".equals( resultMap.get("retcode").toString() ) ){
                String requestNo = resultMap.get("seq_no").toString();
                Map<String,Object> requestMap = new HashMap<>();
                requestMap.put("ent_no",ent_no);
                requestMap.put("seq_no",requestNo);
                String sendBody = String.format("score_id=%s&creid_type=%s&creid_no=%s&name=%s&business_type=%s&query_reason=%s", "12", "1",idCardNumber, name, "1","1");
                sendBody = AESPlus.getStr(AESPlus.encrypt(KEY, sendBody));
                requestMap.put("req_data",sendBody);
                responseWapper = httpClientService.executeGet(REQUEST_CREDIT_URL, requestMap);
                if ( responseWapper.getResultCode() == HttpResponseWapper.SC_OK ){
                    body = responseWapper.getResponse();
                    resultMap = JSON.parseObject(body,HashMap.class);
                    if ( "0".equals( resultMap.get("retcode").toString() ) ){
                        String rspData = resultMap.get("rsp_data").toString();
                        System.out.println("rspData = " + rspData);
                        rspData = new String(AESPlus.decrypt(KEY, rspData));
                        System.out.println("rspData = " + rspData);
                    }
                }
            }
        }
    }
}
