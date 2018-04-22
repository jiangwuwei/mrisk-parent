package com.zoom.risk.platform.thirdparty.facade.adapter.mock;

import com.zoom.risk.platform.thirdparty.minivision.service.MinivisionEntryService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("minivisionMockEntryService")
public class MinivisionMockEntryServiceImpl implements MinivisionEntryService {

    @Override
    public Map<String, Object> invoke(String idCardNumber, String name, String mobile) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("BlackListCheck","1");
        resultMap.put("CrimeInfoCheck","1");
        return resultMap;
    }
}
