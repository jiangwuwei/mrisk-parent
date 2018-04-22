package com.zoom.risk.platform.kafka.consumer.service.impl;

import com.zoom.risk.platform.es.service.EsActionService;
import com.zoom.risk.platform.kafka.consumer.service.EsSaveWorkerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.message.MessageAndMetadata;

/**
 * @author jiangyulin
 *Oct 31, 2015
 */
public class EsSaveWorkerServiceImpl implements EsSaveWorkerService {
	private static final Logger logger = LogManager.getLogger(EsSaveWorkerServiceImpl.class);
	
	private KafkaStream<byte[], byte[]> kafkaStream;
	
	private EsActionService esActionService;

	public EsSaveWorkerServiceImpl(KafkaStream<byte[], byte[]> kafkaStream, EsActionService esActionService) {
		this.kafkaStream = kafkaStream;
		this.esActionService = esActionService;
	}

	public void run() {
		ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
		//will block until come up some messages
		while ( it.hasNext() ){
			MessageAndMetadata<byte[], byte[]> mm = it.next();
			String jsonMessage = new String(mm.message()) ;
			logger.info("KafkaComsumer partition:{}, Topic : {} , Partition key : {}, received original json data from kafka: {} ", mm.partition(), mm.topic(), new String(mm.key()), mm.partition(), jsonMessage );
			esActionService.dispatchEvent(jsonMessage);
		}
	}

}
