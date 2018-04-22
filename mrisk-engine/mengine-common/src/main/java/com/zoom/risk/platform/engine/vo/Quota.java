/**
 * 
 */
package com.zoom.risk.platform.engine.vo;

/**
 * @author jiangyulin Nov 12, 2015
 */
public class Quota extends AbstractBase {
	public static int SOURCE_TYPE_DB_1 = 1;
	public static int SOURCE_TYPE_REDIS_2 =2;
	public static int SOURCE_TYPE_THIRDPARTY_3 =3;
	
	public static int  ACCESS_SOURCE_JADE_1 =1;  //网关库,通过dubbo
	public static int  ACCESS_SOURCE_BI_2 =2;    //BI库
	
	public static int QUOTA_DATA_TYPE_NUMBER_1 = 1;          //数字类型
	public static int QUOTA_DATA_TYPE_STRING_2 = 2;          //字符串
	public static int QUOTA_DATA_TYPE_DATATIME_3 = 3;        //时间
	public static int QUOTA_DATA_TYPE_SINGLE_STRINGLIST = 4; //List
	
	private String sceneNo;
	private String quotaNo; // 指标编号,一个指标一个值
	private Integer sourceType; // 指标来源 1:数据库sql 2: redis缓存
	private Integer accessSource; // 数据库源 1:来自jade库 2:来自BI库',
	private Integer quotaDataType;  //指标的类型  1 数值  2 字符串 
	private String quotaContent; // 指标定义的内容，对于数据库类型的 主要是sql',
	private Integer status; // 规则状态 2:生效 3: 废弃',

	public String getSceneNo() {
		return sceneNo;
	}

	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo;
	}

	public String getQuotaNo() {
		return quotaNo;
	}

	public void setQuotaNo(String quotaNo) {
		this.quotaNo = quotaNo;
	}

	public Integer getSourceType() {
		return sourceType;
	}

	public void setSourceType(Integer sourceType) {
		this.sourceType = sourceType;
	}

	public Integer getAccessSource() {
		return accessSource;
	}

	public void setAccessSource(Integer accessSource) {
		this.accessSource = accessSource;
	}

	public String getQuotaContent() {
		return quotaContent;
	}

	public void setQuotaContent(String quotaContent) {
		this.quotaContent = quotaContent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getQuotaDataType() {
		return quotaDataType;
	}

	public void setQuotaDataType(Integer quotaDataType) {
		this.quotaDataType = quotaDataType;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{id=" + this.id);
		builder.append(",name=" + this.name);
		builder.append(",sceneNo=" + this.sceneNo);
		builder.append(",quotaNo=" + this.quotaNo);
		builder.append(",quotaContent=" + this.quotaContent);
		builder.append(",sourceType=" + this.sourceType);
		builder.append(",accessSource=" + this.accessSource);
		builder.append(",quotaDataType=" + this.quotaDataType);
		builder.append("}");
		return builder.toString();
	}
	
}
