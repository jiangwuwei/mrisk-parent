/**
 * 
 */
package com.zoom.risk.operating.decision.vo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author jiangyulin May 9, 2017
 */
public class Route implements RouteOper{

	private String chineseName;
	private String paramName;
	private int paramDataType;
	private String operation;
	private String value;
	//增加路径的描述
	private String routeName;

	public Route() {
	}

	public Route(String chineseName, String paramName, int paramDataType, String operation, String value, String routeName) {
		this.chineseName = chineseName;
		this.paramName = paramName;
		this.paramDataType = paramDataType;
		this.operation = operation;
		this.value = value;
		this.routeName = routeName;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public int getParamDataType() {
		return paramDataType;
	}

	public void setParamDataType(int paramDataType) {
		this.paramDataType = paramDataType;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getChineseName() {
		return chineseName;
	}

	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}

	@JSONField(serialize=false)
	public String getMevlExpression(){
		StringBuilder builder = new StringBuilder();
		builder.append( this.paramName  + SPACER + this.getOperation() + SPACER );
		if ( this.getParamDataType() == PARAM_DATA_TYPE_DIGITAL || this.getParamDataType() == PARAM_DATA_TYPE_B_DIGITAL){
			builder.append( this.getValue() );
		}else{
			builder.append( "'" + this.getValue() + "'" );
		}
		return builder.toString();
	}
}
