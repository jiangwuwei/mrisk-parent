package com.zoom.risk.operating.decision.proxy.impl;

import com.zoom.risk.operating.common.db.DBSelector;
import com.zoom.risk.operating.decision.po.TQuota;
import com.zoom.risk.operating.decision.po.TQuotaTemplate;
import com.zoom.risk.operating.decision.proxy.DtQuotaProxyService;
import com.zoom.risk.operating.decision.service.DtQuotaService;
import com.zoom.risk.operating.decision.vo.ParamInstanceVo;
import com.zoom.risk.operating.decision.vo.QuotaTemplateVo;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2015/6/1.
 */
@Service("dtQuotaProxyService")
public class DtQuotaProxyServiceImpl implements DtQuotaProxyService {

    @Resource(name="dtQuotaService")
    private DtQuotaService dtQuotaService;

    @Resource(name="sessionManager")
    private SessionManager sessionManager;

    public void saveQuotasForPolicies(String sceneNo, Long[] quotaTemplateId){
        sessionManager.runWithSession(()-> dtQuotaService.saveQuotasForPolicies(sceneNo,quotaTemplateId), DBSelector.OPERATING_MASTER_DB);
    }

    public List<QuotaTemplateVo> findQuotaTemplate(){
        return sessionManager.runWithSession(()-> dtQuotaService.findQuotaTemplate(), DBSelector.OPERATING_MASTER_DB);
    }

    public void delQuotaTemplate(Long id){
        sessionManager.runWithSession(()-> dtQuotaService.delQuotaTemplate(id), DBSelector.OPERATING_MASTER_DB);
    }

    public void saveQuotaTemplate(TQuotaTemplate quotaTemplate){
        sessionManager.runWithSession(()-> dtQuotaService.saveQuotaTemplate(quotaTemplate), DBSelector.OPERATING_MASTER_DB);
    }

    public void updateQuotaTemplate(TQuotaTemplate quotaTemplate){
        sessionManager.runWithSession(()-> dtQuotaService.updateQuotaTemplate(quotaTemplate), DBSelector.OPERATING_MASTER_DB);
    }

    public void delQuota(Long id){
        sessionManager.runWithSession(()-> dtQuotaService.delQuota(id), DBSelector.OPERATING_MASTER_DB);
    }

    public void updateQuota(TQuota quota){
        sessionManager.runWithSession(()-> dtQuotaService.updateQuota(quota), DBSelector.OPERATING_MASTER_DB);
    }

    public TQuotaTemplate findQuotaTemplateById(Long id){
        return sessionManager.runWithSession(()-> dtQuotaService.findQuotaTemplateById(id), DBSelector.OPERATING_MASTER_DB);
    }

    public List<Map<String,Object>> findDimByCode(String code){
        return sessionManager.runWithSession(()-> dtQuotaService.findDimByCode(code), DBSelector.OPERATING_MASTER_DB);
    }

    public List<TQuota> findQuotas(String sceneNo){
        return sessionManager.runWithSession(()-> dtQuotaService.findQuotas(sceneNo), DBSelector.OPERATING_MASTER_DB);
    }


    public TQuota findQuotaById(Long id){
        return sessionManager.runWithSession(()-> dtQuotaService.findQuotaById(id), DBSelector.OPERATING_MASTER_DB);
    }

    public void updateQuotaParam(Long id, ParamInstanceVo vo){
        sessionManager.runWithSession(()-> dtQuotaService.updateQuotaParam(id, vo), DBSelector.OPERATING_MASTER_DB);
    }

    public void batchSaveQuotaTemplate(List<TQuotaTemplate> quotaTemplates){
        sessionManager.runWithSession(()-> dtQuotaService.batchSaveQuotaTemplate(quotaTemplates), DBSelector.OPERATING_MASTER_DB);
    }

    public void updateQuota(Long id, String chineseName, String description){
        sessionManager.runWithSession(()-> dtQuotaService.updateQuota(id,chineseName,description), DBSelector.OPERATING_MASTER_DB);
    }
}
