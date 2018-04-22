package com.zoom.risk.platform.thirdparty.facade.adapter.mock;

import com.zoom.risk.platform.thirdparty.common.utils.ThirdPartyConstants;
import com.zoom.risk.platform.thirdparty.tongdun.service.TongDunEntryService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("tongDunMockEntryService")
public class TongDunMockEntryServiceImpl implements TongDunEntryService {

    @Override
    public Map<String, Object> invoke(String idCardNumber, String accountName, String accountMobile) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put(ThirdPartyConstants.QUOTA_KEY,110);
        return resultMap;
    }

}
