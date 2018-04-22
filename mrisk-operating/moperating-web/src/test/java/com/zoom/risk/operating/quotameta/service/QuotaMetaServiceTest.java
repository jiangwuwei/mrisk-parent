package com.zoom.risk.operating.quotameta.service;

import com.zoom.risk.operating.common.db.DBSelector;
import com.zoom.risk.operating.quotameta.po.ParamInstance;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jiangyulin on 2017/5/10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-config*.xml")
@ActiveProfiles("test")
public class QuotaMetaServiceTest {

    @Resource(name="quotaMetaService")
    private QuotaMetaService quotaMetaService;

    @Resource(name="sessionManager")
    private SessionManager sessionManager;

    @Test
    public void convert(){
        sessionManager.runWithSession( ()-> quotaMetaService.updateRequestParams(11l), DBSelector.OPERATING_MASTER_DB);
    }

    @Test
    public void updateParamInstance(){
        sessionManager.runWithSession(
                ()-> {
                    ParamInstance instance = quotaMetaService.findParamInstance(11L);
                    instance.setName(instance.getName());
                    instance.setChineseName(instance.getChineseName());
                    quotaMetaService.updateParamInstance(instance);

                },
        DBSelector.OPERATING_MASTER_DB);
    }

    @Test
    public void saveQuotaDefinition(){
        sessionManager.runWithSession(
                ()-> {
                    List<Long> list = Arrays.asList(10L,11L,12L);
                    quotaMetaService.saveQuotaDefinitionLink(13L,list.toArray(new Long[1]));
                },
                DBSelector.OPERATING_MASTER_DB);
    }

}
