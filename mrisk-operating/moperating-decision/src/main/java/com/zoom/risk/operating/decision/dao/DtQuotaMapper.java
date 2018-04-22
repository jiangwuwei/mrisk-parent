package com.zoom.risk.operating.decision.dao;

import com.zoom.risk.operating.decision.po.TQuota;
import com.zoom.risk.operating.decision.po.TQuotaTemplate;
import com.zoom.risk.operating.decision.vo.QuotaTemplateVo;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@ZoomiBatisRepository(value="dtQuotaMapper")
public interface DtQuotaMapper {

    public List<QuotaTemplateVo> findQuotaTemplate();

    public TQuotaTemplate findQuotaTemplateById(@Param("id") Long id);

    public void delQuotaTemplate(@Param("id") Long id);

    public void saveQuotaTemplate(@Param("template")TQuotaTemplate quotaTemplate);

    public void updateQuotaTemplate(@Param("template")TQuotaTemplate quotaTemplate);


    public void saveQuotasForPolicies(@Param("sceneNo")String sceneNo, @Param("list")List<TQuota> list);

    public void delQuota(@Param("id") Long id);

    public TQuota findQuotaById(@Param("id") Long id);

    public void updateQuota(@Param("quota") TQuota quota);

    public void updateQuotaByParam(@Param("id")Long id, @Param("chineseName")String chineseName, @Param("description")String description);

    public List<TQuota> findQuotas(@Param("sceneNo") String sceneNo);


    public List<Map<String,Object>> findDimByCode(@Param("code") String code);

    public void updateQuotaParam(@Param("id")Long id, @Param("requestParams")String requestParams);

}