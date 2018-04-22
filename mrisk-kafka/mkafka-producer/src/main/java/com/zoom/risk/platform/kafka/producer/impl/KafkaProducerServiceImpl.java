package com.zoom.risk.platform.kafka.producer.impl;

import com.zoom.risk.platform.kafka.producer.KafkaProducerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;

/**
 * @author jiangyulin
 *Nov 28, 2015
 */
public class KafkaProducerServiceImpl implements KafkaProducerService {
	private static final Logger logger = LogManager.getLogger(KafkaProducerService.class);
	private KafkaProducer<String, String> producer;

	private String bootstrapServers;
	private String defaultTopic;
	private String serializerClass;
	private String kafkaCustomPartition;
	
	public void startup() {
		try {
			Properties props = new Properties();
			props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
			props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, serializerClass);
			props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, serializerClass);
			props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, kafkaCustomPartition);
			props.put(ProducerConfig.RETRIES_CONFIG, 3);
			//props.put(ProducerConfig.RECONNECT_BACKOFF_MS_CONFIG, 20000);
			//props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 20000);
			props.put(ProducerConfig.ACKS_CONFIG, "1");
			producer = new KafkaProducer<String,String>(props);
		} catch (Exception e) {
			logger.error("KafkaProducer: initiation exception !", e);
		}
	}

	public void shutdown(){
		producer.close();
	}

	
	public void sendMessage(String partitinKey, String message) {
		try {
			producer.send(new ProducerRecord<String, String>(defaultTopic, partitinKey, message));
		} catch (Exception e) {
			logger.error("KafkaProducer: message send exception, for the topic " + defaultTopic , e);
		}
	}
	
	public void sendMessage(String partitinKey, String message,String specificalTopic) {
		try {
			producer.send(new ProducerRecord<String, String>(specificalTopic, partitinKey, message));
		} catch (Exception e) {
			logger.error("KafkaProducer: message send exception, for the topic " + specificalTopic , e);
		}
	}

	public void setDefaultTopic(String defaultTopic) {
		this.defaultTopic = defaultTopic;
	}

	public void setSerializerClass(String serializerClass) {
		this.serializerClass = serializerClass;
	}

	public void setKafkaCustomPartition(String kafkaCustomPartition) {
		this.kafkaCustomPartition = kafkaCustomPartition;
	}

	public void setBootstrapServers(String bootstrapServers) {
		this.bootstrapServers = bootstrapServers;
	}
	
}
