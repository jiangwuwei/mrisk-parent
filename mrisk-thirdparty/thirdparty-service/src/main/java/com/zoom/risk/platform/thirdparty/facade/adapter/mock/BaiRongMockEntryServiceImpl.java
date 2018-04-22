package com.zoom.risk.platform.thirdparty.facade.adapter.mock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zoom.risk.platform.thirdparty.bairong.service.BaiRongEntryService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("baiRongMockEntryService")
public class BaiRongMockEntryServiceImpl implements BaiRongEntryService {

    private final static String bairongResult = "{\"courtExecutionStatus\":\"执行中\",\"courtDiscreditStatus\":\"全部未履行\",\"courtExecutionType\":\"最高法执行\",\"courtDiscreditType\":\"失信被执行人\",\"personZT\":\"1\",\"personXD\":\"0\",\"personSD\":\"1\",\"personWF\":\"1\"}";
    @Override
    public Map<String, Object> invoke(String idCardNumber, String userName, String mobile) {
        return JSON.parseObject(bairongResult,new TypeReference<HashMap<String,Object>>(){});
    }
}
