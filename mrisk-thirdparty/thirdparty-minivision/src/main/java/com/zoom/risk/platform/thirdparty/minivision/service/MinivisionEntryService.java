package com.zoom.risk.platform.thirdparty.minivision.service;

import java.util.Map;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */
public interface MinivisionEntryService {

    public Map<String,Object> invoke(String idCardNumber, String name, String mobile);
}
