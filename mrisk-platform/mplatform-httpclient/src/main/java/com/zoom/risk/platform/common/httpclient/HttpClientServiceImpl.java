package com.zoom.risk.platform.common.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * HttpClient服务
 * 利用连接池的机制
 * @author JiangMi
 */
public class HttpClientServiceImpl implements HttpClientService {
	private static final Logger logger = LogManager.getLogger(HttpClientServiceImpl.class);
	private static final int  inactivityTime = 60*1000;
	private PoolingHttpClientConnectionManager poolClientConnMananger;
	private RequestConfig requestConfig;
	private HttpClientBuilder httpClientBuilder;

	private int connTimeout = 0;
	private int conneRequestTimeout = 0;
	private int socketTimeout = 0;
	private int maxConnection = 0;

	public void init() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
		SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();
		//HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.getDefaultHostnameVerifier();
		HostnameVerifier hostnameVerifier = new HostnameVerifier(){
			public boolean verify(String string,SSLSession ssls) {
				return true;
			}
		};
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext,hostnameVerifier);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.getSocketFactory())
				.register("https", sslsf)
				.build();

		poolClientConnMananger = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		poolClientConnMananger.setMaxTotal(maxConnection);
		poolClientConnMananger.setValidateAfterInactivity(inactivityTime);
		
	    requestConfig = RequestConfig.custom()
				.setConnectTimeout(connTimeout)
				.setConnectionRequestTimeout(conneRequestTimeout)
				.setSocketTimeout(socketTimeout)
				.build();
	    
	    httpClientBuilder = HttpClients.custom()
	    		.setConnectionManager(poolClientConnMananger)
	    		.setConnectionManagerShared(true);
	}

	public HttpResponseWapper<String> executePost(String url, Map<String, Object> postParams){
		return this.executePost(url, postParams, "UTF-8");
	}

	@Override
	public HttpResponseWapper<String> executeJsonPost(String url, String jsonStr){
		// 创建httpPost方法,并设置各种超时时间
		HttpResponseWapper<String> zoomResponse = new HttpResponseWapper<>();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		httpPost.setHeader("Content-type", "application/json; charset=utf-8");
		httpPost.setHeader("Connection", "Close");
		StringEntity entity = new StringEntity(jsonStr, Charset.forName("UTF-8"));
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httpPost.setEntity(entity);
		//构建一个httpClient对象， 这个对象负责获取底层链接通过poolConnectionManager
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		String responseContent = null;
		try{
			httpClient = httpClientBuilder.build();
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity resultEntity = response.getEntity();
				responseContent = EntityUtils.toString(resultEntity, Charset.forName("UTF-8"));
				zoomResponse.setResponse(responseContent);
			}else{
				zoomResponse.setResultCode(HttpResponseWapper.SC_ERROR);
				zoomResponse.setErrorMessage(response.getStatusLine().toString());
				logger.error("Request happens error, StatusCode: {}, StatusLine: {}", response.getStatusLine().getStatusCode(), response.getStatusLine());
			}
			response.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.warn("",e);
			}
		}
		return zoomResponse;
	}

	@Override
	public HttpResponseWapper<String> executePost(String url, Map<String, Object> postParams,String charset){
		// 创建httpPost方法,并设置各种超时时间
		HttpResponseWapper<String> zoomResponse = new HttpResponseWapper<>();
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		//构建post请求参数,并设置编码方式为 application/x-www-form-urlencoded以及编码字符
		List<NameValuePair> nameValuePairs = new ArrayList<>();
		for (String key : postParams.keySet()) {
			Object value = postParams.get(key);
			if ( !StringUtils.isEmpty(value) ){
				nameValuePairs.add(new BasicNameValuePair(key, value.toString()));
			}
		}
		StringEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(nameValuePairs, charset);
		} catch (UnsupportedEncodingException e) {
			logger.warn("",e);
		}
		httpPost.setEntity(entity);
		//构建一个httpClient对象， 这个对象负责获取底层链接通过poolConnectionManager 
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		String responseContent = null;
		try{
			httpClient = httpClientBuilder.build();
			response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity resultEntity = response.getEntity();
				responseContent = EntityUtils.toString(resultEntity, charset);
				zoomResponse.setResponse(responseContent);
			}else{
				zoomResponse.setResultCode(HttpResponseWapper.SC_ERROR);
				zoomResponse.setErrorMessage(response.getStatusLine().toString());
				logger.error("Request happens error, StatusCode: {}, StatusLine: {}", response.getStatusLine().getStatusCode(), response.getStatusLine());
			}
			response.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.warn("",e);
			}
		}
		return zoomResponse;
	}

	public HttpResponseWapper<String> executeGet(String url, Map<String, Object> postParams){
		// 创建httpPost方法,并设置各种超时时间
		HttpResponseWapper<String> zoomResponse = new HttpResponseWapper<>();
		StringBuffer buffer = new StringBuffer();
		for (String key : postParams.keySet()) {
			Object value = postParams.get(key);
			if ( !StringUtils.isEmpty(value) ){
				try {
					buffer.append("&" + key + "=" + URLEncoder.encode(value.toString(),"UTF-8"));
				} catch (Exception e) {
					logger.error("",e);
				}
			}
		}
		if ( buffer.length() > 0 ) {
			url = url + "?" + buffer.delete(0,1);
		}
		logger.info(" URL = {}", url);
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);
		//构建一个httpClient对象， 这个对象负责获取底层链接通过poolConnectionManager
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		String responseContent = null;
		try{
			httpClient = httpClientBuilder.build();
			response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity resultEntity = response.getEntity();
				responseContent = EntityUtils.toString(resultEntity, "UTF-8");
				zoomResponse.setResponse(responseContent);
			}else{
				zoomResponse.setResultCode(HttpResponseWapper.SC_ERROR);
				zoomResponse.setErrorMessage(response.getStatusLine().toString());
				logger.error("Request happens error, StatusCode: {}, StatusLine: {}", response.getStatusLine().getStatusCode(), response.getStatusLine());
			}
			response.close();
		}catch(Exception e){
			throw new RuntimeException(e);
		}finally{
			try {
				httpClient.close();
			} catch (IOException e) {
				logger.warn("",e);
			}
		}
		return zoomResponse;
	}

	public void setConnTimeout(int connTimeout) {
		this.connTimeout = connTimeout;
	}

	public void setConneRequestTimeout(int conneRequestTimeout) {
		this.conneRequestTimeout = conneRequestTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}
}
