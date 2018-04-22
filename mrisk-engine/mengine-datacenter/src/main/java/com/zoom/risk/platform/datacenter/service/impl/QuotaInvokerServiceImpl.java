package com.zoom.risk.platform.datacenter.service.impl;

import com.zoom.risk.platform.datacenter.proxy.QuotaDatabaseProxyService;
import com.zoom.risk.platform.datacenter.service.QuotaInvokerService;
import com.zoom.risk.platform.datacenter.service.QuotaJadeService;
import com.zoom.risk.platform.datacenter.service.QuotaRedisService;
import com.zoom.risk.platform.datacenter.service.QuotaThirdPartyService;
import com.zoom.risk.platform.engine.service.FreeMarkerService;
import com.zoom.risk.platform.engine.vo.Quota;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;

/**
 * @author jiangyulin
 *Nov 26, 2015
 */
@Service("quotaInvokerService")
public class QuotaInvokerServiceImpl implements QuotaInvokerService{
    private static final Logger logger = LogManager.getLogger(QuotaInvokerServiceImpl.class);

    @Resource(name="quotaJadeService")
    private QuotaJadeService quotaJadeService;

    @Resource(name="quotaRedisService")
    private QuotaRedisService quotaRedisService;

    @Resource(name="quotaThirdPartyService")
    private QuotaThirdPartyService quotaThirdPartyService;

    @Resource(name="quotaDatabaseProxyService")
    private QuotaDatabaseProxyService quotaDatabaseProxyService;

    @Resource(name="freeMarkerService")
    private FreeMarkerService freeMarkerService;

    @Override
    public Object collectQuota(Quota quota, Map<String,Object> riskInput){
        Object value = null;
        if ( Quota.SOURCE_TYPE_DB_1  == quota.getSourceType() ){
            value = this.collectQuotaFromDatabase(quota, riskInput);
        } else if ( Quota.SOURCE_TYPE_REDIS_2 == quota.getSourceType() ){
            value = this.collectQuotaFromRedis(quota,riskInput);
        } else if ( Quota.SOURCE_TYPE_THIRDPARTY_3 == quota.getSourceType() ){
            Map<String, Object> thirdPartyValue = this.collectQuotaFromThirdParty(quota,riskInput);
            //@TODO to prevent a single value object using a map for third party service， here we try to unify it with the true value not map
            if ( thirdPartyValue.containsKey("quotaKey")){
                value = thirdPartyValue.get("quotaKey");
            }else {
                value = thirdPartyValue;
            }
        }
        return value;
    }

    /**
     * @param quota
     * @return
     */
    public Object collectQuotaFromDatabase(Quota quota, Map<String,Object> riskInput) {
        Object value = null;
        int whichDb = quota.getAccessSource();
        String sql = quota.getQuotaContent();
        try {
            sql = freeMarkerService.merge(quota.getId() + "", riskInput);
            if ( sql != null ) {
                if (whichDb == Quota.ACCESS_SOURCE_JADE_1) {
                    if (Quota.QUOTA_DATA_TYPE_SINGLE_STRINGLIST == quota.getQuotaDataType()) {
                        value = quotaJadeService.getListByInput(sql);
                    } else {
                        value = quotaJadeService.getObjectByInput(sql);
                    }
                } else if (whichDb == Quota.ACCESS_SOURCE_BI_2) {
                    if (Quota.QUOTA_DATA_TYPE_SINGLE_STRINGLIST == quota.getQuotaDataType()) {
                        value = quotaDatabaseProxyService.getListByInput(sql, whichDb + "");
                    } else {
                        value = quotaDatabaseProxyService.getObjectByInput(sql, whichDb + "");
                    }
                }
                if (value == null) {
                    if (Quota.QUOTA_DATA_TYPE_NUMBER_1 == quota.getQuotaDataType()) {
                        value = new BigDecimal(0);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Invoking collectQuotaFromDatabase happens error, whichDb: " + whichDb + " , quotaNo:" + quota.getQuotaNo() + " ,  quotaContent: " + sql, e);
        }
        logger.info("QuotaInvokerService get value[{}] for quota[{}]", value, quota);
        return value;
    }

    public Object collectQuotaFromRedis(Quota quota, Map<String,Object> riskInput) {
        return quotaRedisService.getQuotaObject(quota,riskInput);
    }

    public Map<String, Object> collectQuotaFromThirdParty(Quota quota, Map<String,Object> riskInput) {
        return quotaThirdPartyService.getQuotaObject(quota,riskInput);
    }
}
