package com.zoom.risk.operating.ruleconfig.dao;

import com.zoom.risk.operating.ruleconfig.model.ExtendAttributeDim;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;

import java.util.List;

@ZoomiBatisRepository(value="extendAttributeDimMapper")
public interface ExtendAttributeDimMapper {
    List<ExtendAttributeDim> selectAll();
}