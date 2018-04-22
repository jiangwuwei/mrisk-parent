package com.zoom.risk.operating.antifraud.vo;

import java.math.BigDecimal;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
public class Device {
	
	private String dateStr;	//日期
	private int successCount;	//设备获取成功量
	private int failCount;	//设备获取失败量
	private int totalCount;	//总量
	private double successRatio;	//设备获取成功率
	
	public Device(){
		
	}

	public Device(String dateStr){
		this.dateStr = dateStr;
	}

	public Device(String dateStr,int successCount,int failCount){
		this.dateStr = dateStr;
		this.successCount = successCount;
		this.failCount = failCount;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public int getTotalCount() {
		return successCount + failCount;
	}

	public double getSuccessRatio() {
		BigDecimal rate = new BigDecimal((successCount*100.0)/(successCount+failCount)).setScale(2,BigDecimal.ROUND_HALF_UP);
		return rate.doubleValue();
	}
	
	

}
