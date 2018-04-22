package com.zoom.risk.platform.kafka.common;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author jiangyulin
 * @version 2.0 
 * @date 2015年8月22日
 */
public class CustomPartition implements Partitioner {
	private static final Logger logger = LoggerFactory.getLogger(CustomPartition.class);
	@Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster){
		Integer partitions = cluster.partitionCountForTopic(topic);
		Integer destPartition = 1;
		try{
			destPartition = (int) ( Long.parseLong(key.toString()) % partitions ) ;
		}catch(Exception e){
			logger.error("partition key converting happens error ",e);
		}
		logger.info("Kafak partitions: {}, key: {}, dest partitions : {}, topic: {}", partitions, key , destPartition, topic );
		return destPartition;
	}

	@Override
	public void configure(Map<String, ?> configs) {
	}

	@Override
	public void close() {		
	}

}
