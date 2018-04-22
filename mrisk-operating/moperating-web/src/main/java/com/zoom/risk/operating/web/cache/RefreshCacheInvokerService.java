package com.zoom.risk.operating.web.cache;

import com.zoom.risk.platform.config.service.impl.RefreshCacheServiceAdapter;
import org.springframework.stereotype.Service;

/**
 * 风控网关dubbo服务接口。
 * @author jiangyulin
 * @version 2.0
 * @date 2016/2/18
 */
@Service("zkConfigChangeListener")
public class RefreshCacheInvokerService extends RefreshCacheServiceAdapter {

}
