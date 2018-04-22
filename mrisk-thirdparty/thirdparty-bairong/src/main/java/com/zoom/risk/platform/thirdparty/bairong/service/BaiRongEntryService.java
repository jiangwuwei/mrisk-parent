package com.zoom.risk.platform.thirdparty.bairong.service;

import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */
public interface BaiRongEntryService {

    public Map<String, Object> invoke(String idCardNumber, String userName, String mobile);
}

