package com.zoom.risk.jade.model;

/**
 * 插入或者更新的sql模型，也就是要执行的sql语句
 * @author jiangyulin
 *
 */
public class SqlModel {
	private String sql;
	private Object[] values;
	
	public SqlModel(String sql, Object[] values){
		this.sql = sql;
		this.values = values;
	}

	public String getSql() {
		return sql;
	}

	public Object[] getValues() {
		return values;
	}

	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder("InsertModel [ {sql=" + sql + "},{ values=" );
		for(Object value : values ){
			buffer.append(value+",");
		}
		buffer.delete(buffer.length()-1, buffer.length());
		buffer.append("}] ");
		return buffer.toString();
	}
	
}
