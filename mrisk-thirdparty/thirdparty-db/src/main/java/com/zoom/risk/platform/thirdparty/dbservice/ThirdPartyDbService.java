package com.zoom.risk.platform.thirdparty.dbservice;

public interface ThirdPartyDbService {

    void saveThirdpartyLog(String serviceName,String scene,String riskId,String requestParams,String responseBody,long takingTime);

}
