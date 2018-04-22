package com.zoom.risk.operating.ruleconfig.common;

/**
 * 条件的通用表示
 * @author liyi8
 *
 * 2015年12月13日
 */
public class Condition {

	private String attr; //属性值
	private String oper;
	private String value;
	private boolean isValueInput;
	private String extent; //扩展字段
	private String attrType; //属性的类型

	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean getIsValueInput() {
		return isValueInput;
	}
    public void setIsValueInput(boolean isValueInput) {
		this.isValueInput = isValueInput;
	}

    public String getExtent() {
        return extent;
    }

    public void setExtent(String extent) {
        this.extent = extent;
    }

	public String getAttrType() {
		return attrType;
	}

	public void setAttrType(String attrType) {
		this.attrType = attrType;
	}
}
