/**
 * 
 */
package com.zoom.risk.platform.engine.service;


import com.zoom.risk.platform.engine.mode.EngineDatabase;
import com.zoom.risk.platform.engine.mode.EnginePolicy;
import com.zoom.risk.platform.engine.mode.EnginePolicySet;

import java.util.Map;

/**
 * @author jiangyulin
 *Nov 16, 2015
 */
public interface PolicyService {
	/**
	 * 将所有关于策略集的装载到内存
	 * @return
	 */
	 public EngineDatabase findEnginePolicies();
	 
	 /**按场景将所有的数据分门别类，放入EnginePolices
	  * 保证PolicyRouter中是按照sortOrder倒叙排列
	  * 保证Rule中是按照 executeOrder倒叙排列
	  * @param engineDatabase
	  * @return
	  */
	 public Map<String,EnginePolicySet> buildEngineDatabase(EngineDatabase engineDatabase);

	 /**
	  * 查找某个策略集下权值最大的策略
	  * @param policies
	  * @return
	  */
	 public EnginePolicy getMaxWeightValuePolicy(Map<String,EnginePolicy> policies);
	
}
