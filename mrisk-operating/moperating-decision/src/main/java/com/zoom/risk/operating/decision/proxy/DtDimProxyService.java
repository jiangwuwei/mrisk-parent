package com.zoom.risk.operating.decision.proxy;

import com.zoom.risk.operating.decision.po.TDim;

import java.util.List;

public interface DtDimProxyService {

	public List<TDim> findByCode(String code);

	public void insert(TDim dim);

	public void delete(TDim dim);
}
