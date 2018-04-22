package com.zoom.risk.platform.kafka.consumer.service.impl;

import com.zoom.risk.platform.es.service.EsActionService;
import com.zoom.risk.platform.kafka.consumer.service.KafkaConsumerStartupService;
import com.zoom.risk.platform.kafka.consumer.util.EsConfig;
import kafka.consumer.*;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.*;

/**
 * kafka消费者启动
 * @author jiangyulin
 *
 */
public class KafkaConsumerStartupServiceImpl implements KafkaConsumerStartupService, InitializingBean {
	private static final Logger logger = LogManager.getLogger(KafkaConsumerStartupServiceImpl.class);
	private ConsumerConnector consumer;
	private ConsumerConfig config;
	private EsConfig kafkaConfig;
	private Integer numOfStreams;
	private ThreadPoolTaskExecutor consumerThreadPoolExecutor;
	
	private EsActionService esActionService;
	
	public KafkaConsumerStartupServiceImpl() {
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		config = new ConsumerConfig(kafkaConfig.getKafkaRb());
	}
	
	@Override
	public void startup() {
		consumer = Consumer.createJavaConsumerConnector(config);
		String topic = kafkaConfig.getKafkaRb().getProperty("topic");
		String[] topics = null;
		if ( topic.indexOf(",") > 0 ){
			topics = topic.split(",");
		}else{
			topics = new String[]{topic};
		}
		Map<String,Integer> topicsMap = new HashMap<>();
		for(int i = 0; i < topics.length; i++) {
			topicsMap.put(topics[i], numOfStreams );
		}
		Map<String, List<KafkaStream<byte[], byte[]>>> streamsMap = consumer.createMessageStreams(topicsMap);
		final List<KafkaStream<byte[], byte[]>> resultList = new ArrayList<>();
		streamsMap.forEach((key,value)-> resultList.addAll(value));
		for (final KafkaStream<byte[], byte[]> stream : resultList ) {
			consumerThreadPoolExecutor.submit(new EsSaveWorkerServiceImpl(stream, esActionService));
		}
	}

	@Override
	public void shutdown() {
		try{
			consumer.commitOffsets();
			consumer.shutdown();
		}catch(Exception e){
			logger.error("",e);
		}
	}

	public void setConsumerThreadPoolExecutor(ThreadPoolTaskExecutor consumerThreadPoolExecutor) {
		this.consumerThreadPoolExecutor = consumerThreadPoolExecutor;
	}

	public void setEsActionService(EsActionService esActionService) {
		this.esActionService = esActionService;
	}

	public void setNumOfStreams(Integer numOfStreams) {
		this.numOfStreams = numOfStreams;
	}

	public void setKafkaConfig(EsConfig kafkaConfig) {
		this.kafkaConfig = kafkaConfig;
	}


}
