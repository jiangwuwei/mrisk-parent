package com.zoom.risk.platform.sharding.dbrouter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
 * @author jiangyulin
 *
 */
public class ShardingDataSource extends AbstractRoutingDataSource {
	private static final Logger logger = LogManager.getLogger(ShardingDataSource.class);
	private DsKeyHolder dsKeyHolder;


	@Override
	protected Object determineCurrentLookupKey() {
		String destDsKeyStr = dsKeyHolder.getDsKeyStr();
		if (destDsKeyStr == null) {
			destDsKeyStr = dsKeyHolder.getDefaultValue();
		} else {
			destDsKeyStr = destDsKeyStr.trim();
		}
		//logger.info("You are using dsKeyStr: {}", destDsKeyStr);
		return destDsKeyStr;
	}

	public void setDsKeyHolder(DsKeyHolder dsKeyHolder) {
		this.dsKeyHolder = dsKeyHolder;
	}
}
