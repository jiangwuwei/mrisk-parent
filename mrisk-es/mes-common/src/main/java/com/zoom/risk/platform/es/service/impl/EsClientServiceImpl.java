/**
 * 
 */
package com.zoom.risk.platform.es.service.impl;

import com.zoom.risk.platform.es.service.EsClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * @author jiangyulin
 *Oct 31, 2015
 */
@Service("esClientService")
public class EsClientServiceImpl implements EsClientService {
	private static final Logger logger = LogManager.getLogger(EsClientServiceImpl.class);
	private static final String SPLIT = ",";
	private static final String ADD_SPLIT = ":";
	private static TransportClient client ;

	@Value("${es.cluster.name}")
	private String clusterName;

	@Value("${es.cluster.list}")
	private String clusterList;

	@Override
	public Client getClient() {
		return client;
	}

	@PostConstruct
	public void init(){
		if ( client == null ){
			Settings settings = Settings.settingsBuilder()
					.put("cluster.name", clusterName)
					.put("client.transport.sniff", false)
					.put("http.enabled", true)
	                .put("discovery.zen.ping.multicast.enabled", false)
					.build();
			client = TransportClient.builder()
							.settings(settings)
							.build();
			addAddress(client);
		}
	}

	private void addAddress(TransportClient client){
		String addressStr =  clusterList;
		String[] addresses = addressStr.split(SPLIT);
		for(String add :  addresses){
			String[] adds = add.split(ADD_SPLIT);
			try{
				client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress(adds[0], Integer.parseInt(adds[1]))));
			}catch(Exception e){
				logger.error("",e);
			}
		}
	}
	
	
	public void close(){
		client.close();
	}
}
