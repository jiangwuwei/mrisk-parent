/**
 * 
 */
package com.zoom.risk.platform.es.service;

import org.elasticsearch.client.Client;

/**
 * @author jiangyulin
 *Oct 31, 2015
 */
public interface EsClientService {
	
	public Client getClient();

	public void init();
	
	public void close();
}
