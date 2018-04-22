package com.zoom.risk.operating.ruleconfig.model;

import com.google.gson.Gson;
import com.zoom.risk.operating.ruleconfig.common.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Quota {
	public static final String QUOTA_REGULAR = "(\\s*)Q\\d{4}_\\d{7}(\\s*)";

	private Long id;
	private String sceneNo;
	private String quotaNo; // 指标编号,一个指标一个值
	private Long quotaTemplateId; // 模板外键',
	private Integer sourceType; // 指标来源 1:数据库sql 2: redis缓存
	private Integer accessSource; // 数据库源 1:来自jade库 2:来自BI库',
	private Integer quotaDataType; // 指标的类型 1 数值 2 字符串
	private String quotaContent; // 指标定义的内容，对于数据库类型的 主要是sql',
	private String quotaContentJson; //json格式的指标内容用于前端展示指标
	private Integer status; // 规则状态 2:生效 3: 废弃',

	private String name;

	private String description;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdDate;

	private Date modifiedDate;

	public String getQuotaContentJson() {
		return quotaContentJson;
	}

	public void setQuotaContentJson(String quotaContentJson) {
		this.quotaContentJson = quotaContentJson;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSceneNo() {
		return sceneNo;
	}

	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo == null ? null : sceneNo.trim();
	}

	public String getQuotaNo() {
		return quotaNo;
	}

	public void setQuotaNo(String quotaNo) {
		this.quotaNo = quotaNo == null ? null : quotaNo.trim();
	}

	public Long getQuotaTemplateId() {
		return quotaTemplateId;
	}

	public void setQuotaTemplateId(Long quotaTemplateId) {
		this.quotaTemplateId = quotaTemplateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
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

	public Integer getQuotaDataType() {
		return quotaDataType;
	}

	public void setQuotaDataType(Integer quotaDataType) {
		this.quotaDataType = quotaDataType;
	}

	public String getQuotaContent() {
		return quotaContent;
	}

	public void setQuotaContent(String quotaContent) {
		this.quotaContent = quotaContent == null ? null : quotaContent.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/**
	 * 状态中文名字
	 */
	public String getChStatus(){
		return Constants.STATUS_ARR[status];
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	//方便前端展示
	public String getCreatedDateStr(){
		return createdDate==null?"":DateFormatUtils.format(createdDate, "yyyy-MM-dd HH:mm:ss");
	}

	public QuotaStatisticsTemplate getQuotaStatisticsTemplate(){
		if(StringUtils.isNotBlank(quotaContentJson)){
			return new Gson().fromJson(quotaContentJson,QuotaStatisticsTemplate.class);
		}
		return new QuotaStatisticsTemplate();
	}

	public String getQuotaTypeName(){
		String type = null;
		switch (this.quotaDataType){
			case 1:
				type = Constants.DATA_TYPE_NUMBER;
				break;
			case 2:
				type = Constants.DATA_TYPE_STRING;
				break;
			case 3:
				type = Constants.DATA_TYPE_DATE;
				break;
			case 4:
				type = Constants.DATA_TYPE_STRINGLIST;
				break;
			default:
				type="错误";
		}
		return type;
	}
}