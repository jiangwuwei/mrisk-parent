package com.zoom.risk.platform.thirdparty.bairong.impl;

import com.alibaba.dubbo.rpc.service.GenericService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)  //使用junit4进行测试
@ContextConfiguration(locations={"classpath*:spring/spring-config-*.xml"})
public class BairongGenericServiceTest {

    @Resource
    private GenericService baiRongGenericService;

    @Test
    public void test() {
        try {
            Map<String, Object> outputMap = (Map<String, Object>) baiRongGenericService.$invoke("invokeOld", new String[]{"java.lang.String", "java.lang.String", "java.lang.String", "java.lang.String"}, new Object[]{"140502198811102244", "王亮", "13986671110", "1"});
            System.out.println(outputMap.getClass().getName());
            System.out.println(outputMap);
            if (outputMap.get("resultCode") != null && Integer.valueOf(outputMap.get("resultCode") + "") == 200) {  //200表示成功
                System.out.println(outputMap.get("ex_bad1_name") + "");
                System.out.println(outputMap.get("ex_bad1_cid") + "");
                System.out.println(outputMap.get("ex_bad1_cidtype") + "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
