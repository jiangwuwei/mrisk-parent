/**
 * 
 */
package com.zoom.risk.platform.engine.vo;

/**
 * @author jiangyulin
 *Nov 19, 2015
 */
public class NameValuePair {
	private String key;
	private Object value;
	
	
	public NameValuePair(String key, Object value){
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
