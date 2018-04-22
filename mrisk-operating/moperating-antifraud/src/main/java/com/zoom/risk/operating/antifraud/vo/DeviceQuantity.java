package com.zoom.risk.operating.antifraud.vo;

/**
 * @author jiangyulin, Mar, 17, 2015
 */

public class DeviceQuantity {
	
	private String device;
	
	private int quantity;
	
	private String dateStr;
	
	public DeviceQuantity(){
		
	}
	
	public DeviceQuantity(String device,int quantity){
		this.device = device;
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	
	
	
}
