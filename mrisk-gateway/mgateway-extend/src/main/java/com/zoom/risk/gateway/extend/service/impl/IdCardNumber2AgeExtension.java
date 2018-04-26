package com.zoom.risk.gateway.extend.service.impl;


import com.zoom.risk.gateway.common.utils.RiskResult;
import com.zoom.risk.gateway.extend.common.ContextExtendedAnnotation;
import com.zoom.risk.gateway.extend.service.ContextExtension;
import com.zoom.risk.gateway.common.utils.YearTools;

import java.util.Map;

/**
 * @author jiangyulin
 *Jan 22, 2018
 */
@ContextExtendedAnnotation(value = "idCardNumber2AgeExtension",order = 2, includes = {RiskResult.RISK_BUSI_TYPE_ANTIFRAUD,RiskResult.RISK_BUSI_TYPE_DECISION_TREE,RiskResult.RISK_BUSI_TYPE_SCARD})
public class IdCardNumber2AgeExtension implements ContextExtension {

    @Override
    public Map<String,Object> invoke(Map<String, Object> riskInput) {
        if ( riskInput.containsKey("idCardNumber")){
            String idCardNumber = String.valueOf(riskInput.get("idCardNumber"));
            if ( idCardNumber != null){
                riskInput.put("idCardNumber2Year", YearTools.getRealYearByIdCardNumber(idCardNumber));
            }
        }
        return riskInput;
    }

}
