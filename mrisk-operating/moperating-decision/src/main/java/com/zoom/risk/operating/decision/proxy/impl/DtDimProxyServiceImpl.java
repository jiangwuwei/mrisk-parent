package com.zoom.risk.operating.decision.proxy.impl;

import com.zoom.risk.operating.common.db.DBSelector;
import com.zoom.risk.operating.decision.po.TDim;
import com.zoom.risk.operating.decision.proxy.DtDimProxyService;
import com.zoom.risk.operating.decision.service.DtDimService;
import com.zoom.risk.platform.sharding.dbsharding.SessionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jiangyulin on 2015/6/16.
 */
@Service("dtDimProxyService")
public class DtDimProxyServiceImpl implements DtDimProxyService {
    private static final Logger logger = LogManager.getLogger(DtDimProxyServiceImpl.class);

    @Resource(name="sessionManager")
    private SessionManager sessionManager;

    @Resource(name="dtDimService")
    private DtDimService dtDimService;


    @Override
    public List<TDim> findByCode(String code) {
        return sessionManager.runWithSession( ()->{
            return dtDimService.findByCode(code);
        }, DBSelector.OPERATING_MASTER_DB);
    }

    @Override
    public void insert(TDim dim) {
        sessionManager.runWithSession( ()-> dtDimService.insert(dim), DBSelector.OPERATING_MASTER_DB);
    }

    @Override
    public void delete(TDim dim) {
        sessionManager.runWithSession( ()-> dtDimService.delete(dim), DBSelector.OPERATING_MASTER_DB);
    }
}
