package com.zoom.risk.jade.model;

import java.util.List;

/**
 * 插入或者更新的原始数据
 * @author jiangyulin
 *
 */
public class SaveModel {
	private String sql;
	private List<SceneField> valueFieldList;
	
	public SaveModel(String sql, List<SceneField> valueFieldList){
		this.sql = sql;
		this.valueFieldList = valueFieldList;
	}

	public String getSql() {
		return sql;
	}
 
	public List<SceneField> getValueFieldList() {
		return valueFieldList;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder("SaveModel [sql = " + sql + " ; " );
		for(SceneField field : valueFieldList ){
			buffer.append( field.getDbName() + "=" + field.getStrValue() + ",");
		}
		buffer.append("] ");
		return buffer.toString();
	}
	
}
