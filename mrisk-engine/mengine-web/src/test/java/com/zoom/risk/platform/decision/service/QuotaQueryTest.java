package com.zoom.risk.platform.decision.service;

import com.zoom.risk.platform.decision.cache.DTreeCacheService;
import com.zoom.risk.platform.decision.po.TQuota;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangyulin on 2017/5/25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/spring-config*.xml")
@ActiveProfiles("test")
public class QuotaQueryTest {

    @Resource(name="quotaQueryHelpService")
    private QuotaQueryHelpService quotaQueryHelpService;


    @Resource(name="decisionTreeCacheService")
    private DTreeCacheService decisionTreeCacheService;

    @Test
    public void getFourElementValidateQuotaService(){
        Map<String,Object> riskInput = new HashMap<>();
        riskInput.put("idNumber","342901197303235614");
        riskInput.put("cardBindingMobile","13905663292");
        riskInput.put("cardNumber","6217001770001659409");
        riskInput.put("userName","程剑2");

        TQuota quota = decisionTreeCacheService.getQuota("0903","fourElementValidateQuotaService");
        Object  object = quotaQueryHelpService.getQuotaValue(quota, riskInput);
        System.out.println(object);
    }
}
