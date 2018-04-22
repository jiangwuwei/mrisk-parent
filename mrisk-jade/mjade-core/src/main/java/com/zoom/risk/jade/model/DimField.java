package com.zoom.risk.jade.model;

/**
 * 数据字典的模型
 * @author jiangyulin
 *
 */
public class DimField implements FieldCons{	
	//主键编号
	protected long id;
	//字段类型的类别
	protected long typeId;   
	//关于字段的中文含义,建表的字段描述
	protected String chineseName;          // 字段描述
	//接入传入的参数名称
	protected String paramName;        // 显示名称
	//数据库建表时的字段名称
	protected String dbName;           // 数据库字段名称
	//数据库字段的类型
	protected int dbType;              // 0 varchar, 1 char 2 int 3 bigint  4 decimal 5 datatime  6 timestamp 7 date
	//字段长度,可为空
	protected int length;              // 长度
	//如果是 decimal类型是 可以设置小数位数
	protected int decimalPlace;        // 小数位数 
	//建表示 缺省的值
	protected String defaultValue; 
	//建表时用,表示字段是否可以为空
	protected int nullable = 0;            // 0 yes, 1 no  
	//判断这个字段是否可被更新以及是否是更新条件
	protected int updatable = 0;           // 0 yes, 1 no  2 updatable condition
	//如果这个字段是更新条件时, 判断是否可以为空
	protected int nullableCondition = 1;   // 0 yes, 1 no	//主要用于后续的insert or update 操作
	
	protected String strValue;
	

	public String getParamName() {
		return paramName;
	}
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public int getDbType() {
		return dbType;
	}
	public void setDbType(int dbType) {
		this.dbType = dbType;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getDecimalPlace() {
		return decimalPlace;
	}
	public void setDecimalPlace(int decimalPlace) {
		this.decimalPlace = decimalPlace;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public int getNullable() {
		return nullable;
	}
	public void setNullable(int nullable) {
		this.nullable = nullable;
	}
	public int getUpdatable() {
		return updatable;
	}
	public void setUpdatable(int updatable) {
		this.updatable = updatable;
	}
	public String getStrValue() {
		return strValue;
	}
	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}
	public int getNullableCondition() {
		return nullableCondition;
	}
	public void setNullableCondition(int nullableCondition) {
		this.nullableCondition = nullableCondition;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTypeId() {
		return typeId;
	}
	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}
}
