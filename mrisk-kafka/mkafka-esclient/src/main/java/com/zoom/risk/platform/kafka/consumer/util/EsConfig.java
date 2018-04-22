package com.zoom.risk.platform.kafka.consumer.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import java.util.Properties;

/**
 * @author jiangyulin
 *Oct 31, 2015
 */
public class EsConfig implements InitializingBean {
	private static Properties kafkaRb = new Properties();

	private String topic;
	private String zookeeperConnect;
	private String keySerializer;
	private String valueSerializer;
	private String groupId;
	private boolean autoCommitEnable = true;
	private Integer zookeeperSessionTimeoutMs=5000;
	private Integer zookeeperSyncTimeMs=3000;
	private Integer autoCommitIntervalMs=2000;

	@Override
	public void afterPropertiesSet() throws Exception {
		kafkaRb.put("topic", topic);
		kafkaRb.put("zookeeper.connect", zookeeperConnect);
		kafkaRb.put("key.serializer", keySerializer);
		kafkaRb.put("value.serializer", valueSerializer);
		kafkaRb.put("group.id", groupId);
		kafkaRb.put("auto.commit.enable", "true");
		//Kafka等待Zookeeper的响应时间（毫秒）
		kafkaRb.put("zookeeper.session.timeout.ms", "10000");
		//ZooKeeper 的‘follower’可以落后Master多少毫秒
		kafkaRb.put("zookeeper.sync.time.ms", "5000");
		//consumer更新offerset到Zookeeper的时间
		kafkaRb.put("auto.commit.interval.ms", "2000");
	}


	public Properties getKafkaRb() {
		return kafkaRb;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setZookeeperConnect(String zookeeperConnect) {
		this.zookeeperConnect = zookeeperConnect;
	}

	public void setKeySerializer(String keySerializer) {
		this.keySerializer = keySerializer;
	}

	public void setValueSerializer(String valueSerializer) {
		this.valueSerializer = valueSerializer;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
}
