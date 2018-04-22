package com.zoom.risk.platform.common.httpclient;


import java.util.Map;


/**
 * HttpClient服务
 * @author JiangYuLin
 */
public interface HttpClientService {

	public HttpResponseWapper<String> executePost(String url, Map<String, Object> postParams, String charset);

	public HttpResponseWapper<String> executePost(String url, Map<String, Object> postParams);

	public HttpResponseWapper<String> executeGet(String url, Map<String, Object> postParams);

	public HttpResponseWapper<String> executeJsonPost(String url, String jsonStr);

}