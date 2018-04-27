package com.zoom.risk.gateway.core.proxy;

import java.util.Map;


/**
 * 风控代理。
 * 
 * @author WuHong
 * @version 1.0 
 * @date 2015年7月11日
 */
public interface RuleEngineProxy {

	Map<String,Object> evaluate(Map<String,Object> riskInput);
}
