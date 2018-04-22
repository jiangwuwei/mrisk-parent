package com.zoom.risk.operating.decision.proxy;

import com.zoom.risk.operating.decision.po.TQuota;
import com.zoom.risk.operating.decision.po.TQuotaTemplate;
import com.zoom.risk.operating.decision.vo.ParamInstanceVo;
import com.zoom.risk.operating.decision.vo.QuotaTemplateVo;

import java.util.List;
import java.util.Map;

public interface DtQuotaProxyService {

	public void saveQuotasForPolicies(String sceneNo, Long[] quotaTemplateId);

	public List<QuotaTemplateVo> findQuotaTemplate();

	public TQuotaTemplate findQuotaTemplateById(Long id);

	public void delQuotaTemplate(Long id);

	public void saveQuotaTemplate(TQuotaTemplate quotaTemplate);

	public void batchSaveQuotaTemplate(List<TQuotaTemplate> quotaTemplates);

	public void updateQuotaTemplate(TQuotaTemplate quotaTemplate);



	public TQuota findQuotaById(Long id);

	public void updateQuota(Long id, String chineseName, String description);


	public List<TQuota> findQuotas(String sceneNo);

	public void delQuota(Long id);

	public void updateQuota(TQuota quota);

	public List<Map<String,Object>> findDimByCode(String code);

	public void updateQuotaParam(Long id, ParamInstanceVo vo);
}
