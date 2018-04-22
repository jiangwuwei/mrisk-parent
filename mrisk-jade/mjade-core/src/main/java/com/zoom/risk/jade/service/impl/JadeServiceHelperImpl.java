package com.zoom.risk.jade.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.zoom.risk.jade.exception.DynamicSqlException;
import com.zoom.risk.jade.exception.LackConditionForUpdateException;
import com.zoom.risk.jade.exception.LackValueForUpdateException;
import com.zoom.risk.jade.model.SaveModel;
import com.zoom.risk.jade.model.SceneField;
import com.zoom.risk.jade.model.SqlModel;
import com.zoom.risk.jade.service.JadeServiceHelper;
import com.zoom.risk.jade.utils.JadeConstants;
import com.zoom.risk.jade.utils.SqlUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("jadeServiceHelper")
public class JadeServiceHelperImpl implements JadeServiceHelper {
	private static final Logger logger = LogManager.getLogger(JadeServiceHelperImpl.class);

	protected String getTableName(String gateWayCode){
		return JadeConstants.TABLE_PREFIX + gateWayCode.toLowerCase();
	}
	
	@Override
	public String generateDbScript(String sceneNo, String tableComment, List<SceneField> fields){
		Collections.sort(fields,new SceneFieldComparator());
		String tableName = getTableName(sceneNo);
		StringBuilder buffer = new StringBuilder("create table " + tableName + " (" + JadeConstants.CRLT );
		buffer.append(" id bigint(20) not null auto_increment comment 'auto number'," + JadeConstants.CRLT );
		for(int i = 0 ; i < fields.size(); i++ ){
			SceneField field = fields.get(i);
			buffer.append(JadeConstants.BS + field.getDbName() + JadeConstants.BS );
			//处理各种数据类型
			SqlUtils.buildDataType(buffer, field);
			if ( field.getNullable() ==  SceneField.Nullable_Yes ){
				buffer.append(" null" + JadeConstants.BS);
			}else{
				buffer.append(" not null" + JadeConstants.BS);
			}
			if ( !StringUtils.isEmpty(field.getDefaultValue())){
				if ( field.getDbType() == SceneField.DbType_Char || field.getDbType() == SceneField.DbType_Varchar ){
					buffer.append(" default '" + field.getDefaultValue() + "'" + JadeConstants.BS );
				}else{
					buffer.append(" default " + field.getDefaultValue() + JadeConstants.BS );
				}
			}
			buffer.append(" comment '" + field.getChineseName() + "'," + JadeConstants.CRLT );
		}
		
		buffer.append(" created_time timestamp not null default current_timestamp comment 'created time'," + JadeConstants.CRLT );
		buffer.append(" modified_time timestamp not null default current_timestamp on update current_timestamp comment 'update time'," + JadeConstants.CRLT );
		buffer.append(" primary key (id)" + JadeConstants.CRLT );
		buffer.append(") engine =InnoDB auto_increment=1 default charset=utf8 collate=utf8_bin comment='" + tableComment + "';" + JadeConstants.CRLT );
		String sql = buffer.toString();
		logger.info("\r\n sql = " + sql );
		return sql;
	}
	
 
	protected SaveModel buildOirginalInsert(String tableName, List<SceneField> fields, Map<String, Object> parameterMap){
		Collections.sort(fields,new SceneFieldComparator());
		List<SceneField> valueFields = new ArrayList<SceneField>();
		StringBuilder  buffer = new StringBuilder();
		buffer.append("insert into " + tableName + "(");
		StringBuilder  values = new StringBuilder();
		for(int i = 0 ; i < fields.size(); i++ ){
			SceneField field = fields.get(i);
			String paramName =  field.getParamName();
			//if no value from input map, tricky thing is db field is mandatory
			String value = String.valueOf(parameterMap.get(paramName));
			if ( isNewEmpty(value) ){ 
				continue;
			}
			field.setStrValue(parameterMap.get(paramName)+"");
			valueFields.add(field);
			buffer.append(field.getDbName()+",");
			values.append("?,");
		}
		buffer.delete(buffer.length()-1, buffer.length());
		buffer.append(") values(");
		buffer.append(values.delete(values.length()-1, values.length()));
		buffer.append(")");
		SaveModel insert = new SaveModel(buffer.toString(), valueFields);
		return insert;
	}

	
	
	protected SaveModel buildOirginalUpdate(String tableName, List<SceneField> fields, Map<String, Object> parameterMap) throws DynamicSqlException {
		Collections.sort(fields,new SceneFieldComparator());
		List<SceneField> valueFields = new ArrayList<SceneField>();
		StringBuilder  buffer = new StringBuilder();
		buffer.append("update " + tableName + " set" + JadeConstants.BS);
		StringBuilder  values = new StringBuilder(" where ");
		for(int i = 0 ; i < fields.size(); i++ ){
			SceneField field = fields.get(i);
			if ( field.getUpdatable() == SceneField.Updatable_Yes ){
				String paramName =  field.getParamName();
				String value = String.valueOf(parameterMap.get(paramName));
				if ( isNewEmpty(value) ){ 
					continue;
				}
				field.setStrValue(parameterMap.get(paramName)+"");
				valueFields.add(field);
				buffer.append(field.getDbName()+"=?,");
			}
		}
		//for protection no where condition cases are not allowed
		boolean noCondition = true;
		for(int i = 0 ; i < fields.size(); i++ ){
			SceneField field = fields.get(i);
			if ( field.getUpdatable() == SceneField.Updatable_Condition ){
				String displayName =  field.getParamName();
				String value = (String)parameterMap.get(displayName);
				if ( StringUtils.isEmpty( value) ){
					if (  field.getNullableCondition() == SceneField.Nullable_Condiction_No ){
						throw new LackValueForUpdateException("Lacking value for update condiction for field: " + displayName );
					}else{
						continue;
					}
				}
				field.setStrValue(value);
				valueFields.add(field);
				values.append(field.getDbName()+"=? and ");
				noCondition = false;
			}
		}
		if ( noCondition ){
			throw new LackConditionForUpdateException();
		}
		buffer.delete(buffer.length()-1, buffer.length());
		buffer.append(values.delete(values.length()-4, values.length()));
		//add limit 1
		buffer.append(" limit 1 ");
		SaveModel insert = new SaveModel(buffer.toString(), valueFields);
		return insert;
	}
	
	protected static boolean isNewEmpty(String value){
		boolean flag = false;
		if ( value == null || "".equals(value.trim()) || "null".equals(value)  ){
			flag = true;
		}
		return flag;
	}
	
	protected SqlModel buildOper(String tableName, List<SceneField> fields, Map<String, Object> parameterMap, int operType)throws DynamicSqlException{
		SaveModel model = null;
		if ( operType == 1 ){
			model = this.buildOirginalInsert(tableName, fields, parameterMap);
		}else if ( operType == 2){
			model = this.buildOirginalUpdate(tableName, fields, parameterMap);
		}
		logger.info(model);
		List<SceneField> valueFieldList = model.getValueFieldList();
		Object[] objs = new Object[valueFieldList.size()];
		for(int i = 0; i < valueFieldList.size(); i++ ){
			SceneField field = valueFieldList.get(i); 
			int dbType = field.getDbType();
			String strValue = field.getStrValue();
			objs[i] = SqlUtils.convert(dbType, strValue);
		}
		SqlModel sqlModel = new SqlModel(model.getSql(),objs);
		return sqlModel;
	}
	
	
	@Override
	public SqlModel buildUpdate(String tableName, List<SceneField> fields, Map<String,Object> parameterMap)throws DynamicSqlException{
		return this.buildOper(tableName, fields, parameterMap, 2);
	}
 
	@Override
	public SqlModel buildInsert(String tableName, List<SceneField> fields, Map<String,Object> parameterMap)throws DynamicSqlException{
		return this.buildOper(tableName, fields, parameterMap, 1);
	}
	
	public static class SceneFieldComparator implements Comparator<SceneField>{
		public int compare(SceneField o1, SceneField o2) {
			long value = o1.getId() - o2.getId();
			return value >=0 ? 1 : -1;
		}
	}
}
