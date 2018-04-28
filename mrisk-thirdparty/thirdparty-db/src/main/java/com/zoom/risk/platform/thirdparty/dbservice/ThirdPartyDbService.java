package com.zoom.risk.platform.thirdparty.dbservice;

public interface ThirdPartyDbService {

    public void saveThirdpartyLog(String serviceName,String scene,String riskId,String requestParams,String responseBody,long takingTime);

}
