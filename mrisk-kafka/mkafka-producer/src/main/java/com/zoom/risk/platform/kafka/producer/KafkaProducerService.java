/**
 * 
 */
package com.zoom.risk.platform.kafka.producer;

/**
 * @author jiangyulin Nov 28, 2015
 */
public interface KafkaProducerService {
	
	public void startup();

	public void shutdown();

	public void sendMessage(String partitinKey, String message);

	public void sendMessage(String partitinKey, String message, String specificalTopic);
}
