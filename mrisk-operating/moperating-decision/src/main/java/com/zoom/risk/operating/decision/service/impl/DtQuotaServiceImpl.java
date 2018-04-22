package com.zoom.risk.operating.decision.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.zoom.risk.operating.decision.dao.DtQuotaMapper;
import com.zoom.risk.operating.decision.po.TQuota;
import com.zoom.risk.operating.decision.po.TQuotaTemplate;
import com.zoom.risk.operating.decision.service.DtQuotaService;
import com.zoom.risk.operating.decision.service.RiskDecisionTreeService;
import com.zoom.risk.operating.decision.vo.ParamInstanceVo;
import com.zoom.risk.operating.decision.vo.QuotaTemplateVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2017/6/1.
 */
@Service("dtQuotaService")
@Transactional
public class DtQuotaServiceImpl implements DtQuotaService {

    @Resource(name="dtQuotaMapper")
    private DtQuotaMapper dtQuotaMapper;

    @Resource(name="riskDecisionTreeService")
    private RiskDecisionTreeService riskDecisionTreeService;

    public void saveQuotasForPolicies(String sceneNo, Long[] quotaTemplateId){
        List<TQuota> templates = new ArrayList<>();
        for( int i = 0; i < quotaTemplateId.length; i++ ){
            TQuotaTemplate template = dtQuotaMapper.findQuotaTemplateById(quotaTemplateId[i]);
            TQuota quota = TQuota.convert(template,sceneNo);
            quota.setQuotaNo(riskDecisionTreeService.getNextNumber("TQ",sceneNo));
            templates.add(quota);
        }
        dtQuotaMapper.saveQuotasForPolicies(sceneNo, templates);
    }

    public List<QuotaTemplateVo> findQuotaTemplate(){
        return dtQuotaMapper.findQuotaTemplate();
    }

    public void delQuota(Long id){
        dtQuotaMapper.delQuota(id);
    }

    public void updateQuota(TQuota quota){
        dtQuotaMapper.updateQuota(quota);
    }

    public TQuotaTemplate findQuotaTemplateById(Long id){
        return dtQuotaMapper.findQuotaTemplateById(id);
    }

    public List<Map<String,Object>> findDimByCode( String code){
        return dtQuotaMapper.findDimByCode(code);
    }

    public void delQuotaTemplate(Long id){
        dtQuotaMapper.delQuotaTemplate(id);
    }

    public void saveQuotaTemplate(TQuotaTemplate quotaTemplate){
        dtQuotaMapper.saveQuotaTemplate(quotaTemplate);
    }

    public void batchSaveQuotaTemplate(List<TQuotaTemplate> quotaTemplates){
        quotaTemplates.forEach( template -> dtQuotaMapper.saveQuotaTemplate(template));
    }
    
    public void updateQuotaTemplate(TQuotaTemplate quotaTemplate){
        dtQuotaMapper.updateQuotaTemplate(quotaTemplate);
    }

    public List<TQuota> findQuotas(String sceneNo){
        return dtQuotaMapper.findQuotas(sceneNo);
    }

    public TQuota findQuotaById(Long id){
        return dtQuotaMapper.findQuotaById(id);
    }

    public void updateQuota(Long id, String chineseName, String description){
        dtQuotaMapper.updateQuotaByParam(id,chineseName,description);
    }

    public void updateQuotaParam(Long id, ParamInstanceVo vo){
        TQuota quota = dtQuotaMapper.findQuotaById(id);
        List<ParamInstanceVo> paramsList = JSON.parseObject(quota.getRequestParams(), new TypeToken<ArrayList<ParamInstanceVo>>(){}.getType());
        for(ParamInstanceVo v : paramsList ){
            if ( v.getName().equals(vo.getName()) ){
                v.setMandatory(vo.getMandatory());
                if ( vo.getDefaultValue() != null && (!"".equals(vo.getDefaultValue())) ) {
                    v.setDefaultValue(vo.getDefaultValue());
                }
                if ( vo.getDescription() != null && (!"".equals(vo.getDescription())) ) {
                    v.setDescription(vo.getDescription());
                }
                break;
            }
        }
        quota.setRequestParams(JSON.toJSONString(paramsList));
        dtQuotaMapper.updateQuotaParam(id, quota.getRequestParams());
    }
}
