package com.zoom.risk.operating.quotameta.service;

import com.zoom.risk.operating.quotameta.po.ParamInstance;
import com.zoom.risk.operating.quotameta.po.ParamTemplate;
import com.zoom.risk.operating.quotameta.po.QuotaDefinition;
import com.zoom.risk.operating.quotameta.vo.QuotaDefinitionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by jiangyulin on 2015/5/25.
 */
public interface QuotaMetaService {

    public List<ParamInstance> findByQuotaId(Long quotaId);

    public void updateRequestParams(Long quotaId);

    public QuotaDefinition findDefinitionById(Long id);

    public void delQuotaDefinition(Long id);

    public void updateQuotaDefinition(QuotaDefinition quotaDefinition);

    public void sycStatusQuotaDefinition(Long[] ids);

    public void saveQuotaDefinition(QuotaDefinition quotaDefinition);

    public void saveQuotaDefinitionLink(Long quotaId, Long[] paramTemplateIds);

    public void delParamInstance(Long paramInstanceId);

    public void updateParamInstance(ParamInstance instance);

    public ParamInstance findParamInstance(Long id);

    public void saveParamTemplate(@Param("template") ParamTemplate template);

    public void updateParamTemplate(ParamTemplate pramTemplate);

    public void delParamTemplate(Long templateId);

    public ParamTemplate findParamTemplate(Long id);

    public  List<ParamTemplate> findAllParamTemplate();

    public List<QuotaDefinitionVo> findAllQuotaDefinition();

    public List<Map<String,Object>> findSourceDim();
}
