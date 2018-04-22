package com.zoom.risk.operating.ruleconfig.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import static com.zoom.risk.operating.ruleconfig.common.Constants.POLICY_PATTERN_ARR;

public class Policy {
    private Long id;

    private String name;
	private String sceneNo;                //场景号4位
	private Integer policyPattern;        //策略模式 1: 最坏模式 2:权重模式
	private Integer weightValue;            //权重取值, 只有policy_pattern == 2 是才有效   min,max 形式
	private String weightGrade;           //权值分档  比如 300,500
	private String policyNo;
	private Integer min;
	private Integer max;
	private Integer status;
	private String description;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    private Date modifiedDate;

    public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getPolicyPattern() {
        return policyPattern;
    }

    public void setPolicyPattern(Integer policyPattern) {
        this.policyPattern = policyPattern;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(Integer weightValue) {
        this.weightValue = weightValue;
    }

    public String getWeightGrade() {
        return weightGrade;
    }

    public void setWeightGrade(String weightGrade) {
        this.weightGrade = weightGrade == null ? null : weightGrade.trim();
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

	public Integer getMin() {
		return this.min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return this.max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}
    
	public String getChPatternName(){
		return POLICY_PATTERN_ARR[policyPattern.intValue()];
	}

	//方便前端展示
	public String getCreatedDateStr(){
		return DateFormatUtils.format(createdDate, "yyyy-MM-dd HH:mm:ss");
	}
	
	public Pair<String, String> getWeightGradePair(){
		if(StringUtils.isNotBlank(weightGrade)){
			weightGrade = weightGrade.replace("[", "");
			weightGrade = weightGrade.replace("]", "");
			String[] paras = weightGrade.split(",");
			return Pair.of(paras[0], paras[1]);
		}
		return Pair.of("", "");
	}
}