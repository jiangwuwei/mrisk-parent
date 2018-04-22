package com.zoom.risk.operating.antifraud.vo;

/**
 * @author jiangyulin, Mar, 17, 2015
 */

public class Event {
	
	private String dateStr; //日期
	private int passCount;	//通过量
	private int verifyCount;	//人工审核量
	private int refuseCount;	//拒绝量
	private int totalCount;	//总量
	
	public Event(){
		
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public int getPassCount() {
		return passCount;
	}

	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}

	public int getVerifyCount() {
		return verifyCount;
	}

	public void setVerifyCount(int verifyCount) {
		this.verifyCount = verifyCount;
	}

	public int getRefuseCount() {
		return refuseCount;
	}

	public void setRefuseCount(int refuseCount) {
		this.refuseCount = refuseCount;
	}

	public int getTotalCount() {
		return passCount + verifyCount + refuseCount;
	}
	
	

}
