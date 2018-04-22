package com.zoom.risk.operating.decision.service;

import com.zoom.risk.operating.decision.po.TDim;

import java.util.List;

public interface DtDimService {

	public List<TDim> findByCode(String code);

	public void insert(TDim dim);

	public void delete(TDim dim);
}
