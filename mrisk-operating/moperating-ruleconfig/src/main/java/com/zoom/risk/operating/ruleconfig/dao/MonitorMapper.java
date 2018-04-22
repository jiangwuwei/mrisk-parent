package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.DtDim;
import com.zoom.risk.operating.ruleconfig.model.DtParamTemplate;
import com.zoom.risk.operating.ruleconfig.model.DtQuotaTemplate;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

import java.util.List;
import java.util.Map;

@ZoomiBatisRepository(value = "monitorMapper")
public interface MonitorMapper {

    List<DtDim> selectAllDtDim();

    List<DtQuotaTemplate> selectAllDtQuotaName();

    List<DtParamTemplate> selectAllDtParamTemplate();

    List<Map<String,Object>> selectMapForScardParam(Long scardId);

}