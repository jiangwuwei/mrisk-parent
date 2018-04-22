package com.zoom.risk.operating.quotameta.dao;

import com.zoom.risk.operating.quotameta.po.ParamInstance;
import com.zoom.risk.operating.quotameta.po.ParamTemplate;
import com.zoom.risk.operating.quotameta.po.QuotaDefinition;
import com.zoom.risk.operating.quotameta.vo.QuotaDefinitionVo;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author jiangyulin, Mar, 17, 2015
 */

@ZoomiBatisRepository(value="quotaMetaMapper")
public interface QuotaMetaMapper {
	
	public List<ParamInstance> findByQuotaId(@Param("quotaId") Long quotaId);

	public void updateRequestParams(@Param("quotaId")Long quotaId, @Param("requestParams") String requestParams);

	public QuotaDefinition findDefinitionById(@Param("id") Long id);

	public void delQuotaDefinition(@Param("id") Long id);

	public void saveQuotaDefinition(QuotaDefinition quotaDefinition);

	public void updateQuotaDefinition(QuotaDefinition quotaDefinition);

	public void sycStatusQuotaDefinition(@Param("id")Long id);

	public void saveQuotaDefinitionLink(@Param("quotaId")Long quotaId, @Param("paramTemplateList") List<ParamTemplate> paramTemplateList);

	public void updateParamInstance(ParamInstance instance);

	public ParamInstance findParamInstance(@Param("id")Long id);

	public void delParamInstance(@Param("paramInstanceId")Long paramInstanceId);

	public void saveParamTemplate(@Param("template") ParamTemplate template);

	public ParamTemplate findParamTemplate(@Param("id")Long id);

	public void updateParamTemplate(ParamTemplate pramTemplate);

	public void delParamTemplate(Long templateId);

	public List<ParamTemplate> findAllParamTemplate();

	public List<QuotaDefinitionVo> findAllQuotaDefinition();

	public List<Map<String,Object>> findSourceDim();

}
