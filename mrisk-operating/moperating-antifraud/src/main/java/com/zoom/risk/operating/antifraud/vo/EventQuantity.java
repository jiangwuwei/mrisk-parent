package com.zoom.risk.operating.antifraud.vo;

/**
 * @author jiangyulin, Mar, 17, 2015
 */

public class EventQuantity {
	
	private String decisionCode;
	
	private int quantity;
	
	private String dateStr;

	private Double ratio;
	
	public EventQuantity(){
		
	}
	
	public EventQuantity(String decisionCode,int quantity){
		this.decisionCode = decisionCode;
		this.quantity = quantity;
	}
	
	public EventQuantity(String decisionCode,int quantity,String dateStr){
		this.decisionCode = decisionCode;
		this.quantity = quantity;
		this.dateStr = dateStr;
	}

	public String getDecisionCode() {
		return decisionCode;
	}

	public void setDecisionCode(String decisionCode) {
		this.decisionCode = decisionCode;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }
}
