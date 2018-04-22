package com.zoom.risk.platform.thirdparty.tongdun.service;

import java.util.Map;

/**
 * @author jiangyulin
 *Oct 26, 2015
 */
public interface TongDunEntryService {
    public final String PARTNER_CODE = "partner_code";
    public final String APP_NAME = "app_name";
    public final String PARTNER_KEY = "partner_key";


    public Map<String, Object> invoke(String idCardNumber, String accountName, String accountMobile );


}
