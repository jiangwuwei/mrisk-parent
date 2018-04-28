package com.zoom.risk.platform.thirdparty.common.service;

/**
 * @author jiangyulin
 *Oct 25, 2015
 */
public interface ThreadLocalService {

    public String getRiskId();

    public void putRiskId(String riskId);

    public String getServiceName();

    public void putServiceName(String serviceName);

    public String getScene();

    public void putScene(String scene);

    public void remove();

}
