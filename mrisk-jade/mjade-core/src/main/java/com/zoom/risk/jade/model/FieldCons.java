package com.zoom.risk.jade.model;
/**
 * 字典表中常量
 * @author jiangyulin
 *
 */
public interface FieldCons {
	
	public static final int Nullable_No = 1;
	public static final int Nullable_Yes = 0;
	
	public static final int Nullable_Condiction_No = 1;
	public static final int Nullable_Condiction_Yes = 0;
	
	public static final int Updatable_No = 1;
	public static final int Updatable_Yes = 0;
	public static final int Updatable_Condition = 2;
	
	
	public static final int DbType_Varchar = 0;
	public static final int DbType_Char = 1;
	public static final int DbType_Int = 2;
	public static final int DbType_Bigint = 3;
	public static final int DbType_Decimal = 4;
	public static final int DbType_Datetime = 5;
	public static final int DbType_Date = 7;
	public static final int DbType_timestamp = 6;
	

}
