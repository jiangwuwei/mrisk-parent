package com.zoom.risk.operating.quotameta.service.impl;

import com.alibaba.fastjson.JSON;
import com.zoom.risk.operating.quotameta.dao.QuotaMetaMapper;
import com.zoom.risk.operating.quotameta.po.ParamInstance;
import com.zoom.risk.operating.quotameta.po.ParamTemplate;
import com.zoom.risk.operating.quotameta.po.QuotaDefinition;
import com.zoom.risk.operating.quotameta.service.QuotaMetaService;
import com.zoom.risk.operating.quotameta.vo.ParamInstanceVo;
import com.zoom.risk.operating.quotameta.vo.QuotaDefinitionVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2015/5/10.
 */
@Service("quotaMetaService")
public class QuotaMetaServiceImpl implements QuotaMetaService {
    private static final Logger logger = LogManager.getLogger(QuotaMetaServiceImpl.class);

    @Resource(name="quotaMetaMapper")
    private QuotaMetaMapper quotaMetaMapper;

    @Transactional(readOnly = true)
    public List<ParamInstance> findByQuotaId(Long quotaId){
        return  quotaMetaMapper.findByQuotaId(quotaId);
    }

    @Transactional(readOnly = false)
    public void updateRequestParams(Long quotaId){
        List<ParamInstance> list = quotaMetaMapper.findByQuotaId(quotaId);
        List<ParamInstanceVo> resultList = new ArrayList<>(list.size());
        list.forEach( p-> resultList.add(p.copyVo()));
        String requestParams = JSON.toJSONString(resultList);
        logger.info("requestParams : {}", requestParams );
        quotaMetaMapper.updateRequestParams(quotaId, requestParams);
    }

    @Transactional(readOnly = false)
    public void saveQuotaDefinition(QuotaDefinition quotaDefinition){
        quotaMetaMapper.saveQuotaDefinition(quotaDefinition);
    }


    @Transactional(readOnly = false)
    public void saveQuotaDefinitionLink(Long quotaId, Long[] paramTemplateIds){
        List<ParamTemplate> list = new ArrayList<>();
        for (int i =0 ; i < paramTemplateIds.length; i++ ){
            list.add(quotaMetaMapper.findParamTemplate(paramTemplateIds[i]));
        }
        quotaMetaMapper.saveQuotaDefinitionLink(quotaId,list);
        this.updateRequestParams(quotaId);
    }

    @Transactional(readOnly = false)
    public void delParamInstance(Long paramInstanceId){
        Long quotaId = quotaMetaMapper.findParamInstance(paramInstanceId).getQuotaId();
        quotaMetaMapper.delParamInstance(paramInstanceId);
        this.updateRequestParams(quotaId);
    }

    @Transactional(readOnly = false)
    public void updateParamInstance(ParamInstance instance){
        quotaMetaMapper.updateParamInstance(instance);
        this.updateRequestParams(instance.getQuotaId());
    }

    @Transactional(readOnly = true)
    public ParamInstance findParamInstance(Long paramInstanceId){
        return quotaMetaMapper.findParamInstance(paramInstanceId);
    }

    @Transactional(readOnly = false)
    public void updateParamTemplate(ParamTemplate paramTemplate){
        quotaMetaMapper.updateParamTemplate(paramTemplate);
    }

    @Transactional(readOnly = false)
    public void delParamTemplate(Long templateId){
        quotaMetaMapper.delParamTemplate(templateId);
    }

    @Transactional(readOnly = true)
    public  List<ParamTemplate> findAllParamTemplate(){
        return quotaMetaMapper.findAllParamTemplate();
    }

    @Transactional(readOnly = true)
    public List<QuotaDefinitionVo> findAllQuotaDefinition(){
        return quotaMetaMapper.findAllQuotaDefinition();
    }

    @Transactional(readOnly = true)
    public ParamTemplate findParamTemplate(Long id){
        return quotaMetaMapper.findParamTemplate(id);
    }

    @Transactional(readOnly = false)
    public void saveParamTemplate(ParamTemplate template){
        quotaMetaMapper.saveParamTemplate(template);
    }

    @Transactional(readOnly = true)
    public QuotaDefinition findDefinitionById(Long id){
        return quotaMetaMapper.findDefinitionById(id);
    }

    @Transactional(readOnly = false)
    public void delQuotaDefinition(Long id){
       if ( quotaMetaMapper.findDefinitionById(id).getSycStatus() == 1 )
           throw new RuntimeException("The Quota has been syc already!");
        quotaMetaMapper.delQuotaDefinition(id);
    }

    @Transactional(readOnly = true)
    public List<Map<String,Object>> findSourceDim(){
        return quotaMetaMapper.findSourceDim();
    }

    @Transactional(readOnly = false)
    public void updateQuotaDefinition(QuotaDefinition quotaDefinition){
        quotaMetaMapper.updateQuotaDefinition(quotaDefinition);
    }

    @Transactional(readOnly = false)
    public void sycStatusQuotaDefinition(Long[] ids){
        for( int i = 0 ; i < ids.length; i++ ) {
            quotaMetaMapper.sycStatusQuotaDefinition(ids[i]);
        }
    }
}
