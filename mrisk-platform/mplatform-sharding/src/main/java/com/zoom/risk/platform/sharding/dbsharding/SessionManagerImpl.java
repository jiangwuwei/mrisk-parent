/**
 * 
 */
package com.zoom.risk.platform.sharding.dbsharding;

import com.zoom.risk.platform.sharding.dbrouter.DsKeyHolder;

/**
 * @author jiangyulin
 *Nov 27, 2016
 */
public class SessionManagerImpl implements SessionManager {

	private DsKeyHolder dsKeyHolder;
	
	@Override
	public <T> T runWithSession(ServiceExecutor<T> s, String dsKey) {
		T t = null;
		try{
			dsKeyHolder.setDsKeyStr(dsKey);
			t = s.execute();
		}finally{
			dsKeyHolder.cleanupDsKey();
		}
		return t;
	}

	public void runWithSession(ServiceExecutorWithoutResult s, String dsKey) {
		try{
			dsKeyHolder.setDsKeyStr(dsKey);
			s.execute();
		}finally{
			dsKeyHolder.cleanupDsKey();
		}
	}

	public void setDsKeyHolder(DsKeyHolder dsKeyHolder) {
		this.dsKeyHolder = dsKeyHolder;
	}
}
