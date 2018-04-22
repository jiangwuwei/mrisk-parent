package com.zoom.risk.operating.ruleconfig.service;

import com.google.common.collect.Lists;
import com.zoom.risk.operating.ruleconfig.dao.QuotaTemplateMapper;
import com.zoom.risk.operating.ruleconfig.model.QuotaTemplate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liyi8 on 2017/2/23.
 */
@Service("quotaTemplateService")
public class QuotaTemplateService {

    private static final Logger logger  = LogManager.getLogger(QuotaTemplateService.class);

    @Autowired
    private QuotaTemplateMapper quotaTemplateMapper;

    public QuotaTemplate selectById(long id){
        try {
            return quotaTemplateMapper.selectByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    public List<QuotaTemplate> selectAll(){
        try {
            return quotaTemplateMapper.selectAll();
        } catch (Exception e) {
            logger.error(e);
        }
        return Lists.newArrayList();
    }
}

