package com.zoom.risk.operating.decision.dao;

import com.zoom.risk.operating.decision.po.TDim;
import com.zoom.risk.platform.common.annotation.ZoomiBatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@ZoomiBatisRepository(value="dtDimMapper")
public interface DtDimMapper {

    public List<TDim> findByCode(@Param("code")String code);

    public void insert(@Param("dim")TDim dim);

    public void delete(@Param("dim")TDim dim);

}