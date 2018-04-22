/**
 * 
 */
package com.zoom.risk.platform.common.httpclient;

/**http调用返回结果
 * 0. http 返回 200
 * 1. http 返回 非200
 * @author JiangMi
 */
public class HttpResponseWapper<T> {
	public static final int SC_OK = 0;
	public static final int SC_ERROR = 1;
	
	private T response;
	
	private int resultCode = SC_OK ;  // 0 返回 200  1 返回结果 非 200  

	private String errorMessage;      //http请求发生错误时的详细信息
	
	public T getResponse() {
		return response;
	}
	public void setResponse(T response) {
		this.response = response;
	}

	public int getResultCode() {
		return resultCode;
	}
	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
