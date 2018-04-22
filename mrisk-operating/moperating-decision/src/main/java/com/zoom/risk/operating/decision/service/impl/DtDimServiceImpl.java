package com.zoom.risk.operating.decision.service.impl;

import com.zoom.risk.operating.decision.dao.DtDimMapper;
import com.zoom.risk.operating.decision.po.TDim;
import com.zoom.risk.operating.decision.service.DtDimService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("dtDimService")
public class DtDimServiceImpl implements DtDimService {

	@Resource(name="dtDimMapper")
	private DtDimMapper dtDimMapper;

	@Override
	public List<TDim> findByCode(String code) {
		return dtDimMapper.findByCode(code);
	}

	@Override
	public void insert(TDim dim) {
		dtDimMapper.insert(dim);
	}

	@Override
	public void delete(TDim dim) {
		dtDimMapper.delete(dim);
	}
}
