package com.zoom.risk.jade.utils;

import static com.zoom.risk.jade.utils.JadeConstants.BS;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zoom.risk.jade.model.SceneField;


/**
 * @author jiangyulin
 */
public class SqlUtils {
	private static final Logger logger = LogManager.getLogger(SqlUtils.class);
	
	/**
	 * 进入此处的strValue不为空
	 * @param dbType
	 * @param strValue
	 * @return
	 */
	public static Object convert(int dbType, String strValue){
		Object value = strValue;
		if ( dbType == SceneField.DbType_Bigint ){
			value = SqlUtils.parseLong(value);
		}else if ( dbType == SceneField.DbType_Int ){
			value = SqlUtils.parseInt(value);
		}else if ( dbType == SceneField.DbType_Decimal ){
			value = SqlUtils.parseBigDecimal(value);
		}else if ( dbType == SceneField.DbType_Char || dbType == SceneField.DbType_Varchar){
			value = strValue;
		}else if ( dbType == SceneField.DbType_Datetime || dbType == SceneField.DbType_Date){
			//日期类型的,前台统一用 毫秒数来 接入
			try {
				if ( strValue.length() > 10 ){
					SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					value = timestampFormat.parse(strValue);
				}else{
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					value = dateFormat.parse(strValue);
				}
			} catch (ParseException e) {
				logger.warn("Converting String (" +  strValue + ") to Datatime or Date happens error");
			}

		}else if ( dbType == SceneField.DbType_timestamp ){
			try{
				SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				value = timestampFormat.parse(strValue);
			} catch (Exception e) {
				logger.warn("Converting String (" +  strValue + ") to Timestamp happens error");
			}
		}
		return value;
	}
	
	public static void buildDataType(StringBuilder buffer, SceneField field){
		if ( field.getDbType() == SceneField.DbType_Varchar ){
			buffer.append(" varchar(" + field.getLength() + ")" + BS );
		}else if ( field.getDbType() == SceneField.DbType_Char ){
			buffer.append(" char(" + field.getLength() + ")" + BS );
		}else if ( field.getDbType() == SceneField.DbType_Int ){
			buffer.append(" int" + BS );
		}else if ( field.getDbType() == SceneField.DbType_Bigint ){
			buffer.append(" bigint(" + field.getLength() + ")" + BS );
		}else if ( field.getDbType() == SceneField.DbType_Decimal ){
			buffer.append(" decimal(" + field.getLength() + "," + field.getDecimalPlace() + ")" + BS );
		}else if ( field.getDbType() == SceneField.DbType_Datetime ){
			buffer.append(" datetime" + BS );
		}else if ( field.getDbType() == SceneField.DbType_timestamp ){
			buffer.append(" timestamp" + BS );
		}else if ( field.getDbType() == SceneField.DbType_Date ){
			buffer.append(" date" + BS );
		}
	}
	
	public static Long parseLong(Object value){
		long v = 0L;
		try{
			v = Long.parseLong(value.toString());
		}catch(Exception e){
			logger.warn("Converting to long happens errors");
		}
		return v;
	}
	
	public static Integer parseInt(Object value){
		int v = 0;
		try{
			v = Integer.parseInt(value.toString());
		}catch(Exception e){
			logger.warn("Converting to int happens errors");
		}
		return v;
	}
	
	public static BigDecimal parseBigDecimal(Object value){
		BigDecimal v = new BigDecimal(0);
		try{
			v = new BigDecimal(value.toString());
		}catch(Exception e){
			logger.warn("Converting to int happens errors");
		}
		return v;
	}
	
	public static void main(String args[]){
		System.out.println(parseBigDecimal("500.00"));
	}
}
