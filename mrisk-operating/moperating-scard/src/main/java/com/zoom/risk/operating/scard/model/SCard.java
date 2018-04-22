package com.zoom.risk.operating.scard.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
public class SCard {
    private Long id;
    private String name;
	private String sceneNo;                //场景号4位
	private Integer weightValue;            //评分卡执行权重
	private String scardNo;
	private Integer weightFlag;             //参数变量是否有权重  0 没有 1 有
	private Integer percentageFlag;         //是否是百分比  0 不是 1 是
	private Integer status;                 //
	private String description;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    private Date modifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSceneNo() {
        return sceneNo;
    }

    public void setSceneNo(String sceneNo) {
        this.sceneNo = sceneNo;
    }

    public Integer getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(Integer weightValue) {
        this.weightValue = weightValue;
    }

    public String getScardNo() {
        return scardNo;
    }

    public void setScardNo(String scardNo) {
        this.scardNo = scardNo;
    }

    public Integer getWeightFlag() {
        return weightFlag;
    }

    public void setWeightFlag(Integer weightFlag) {
        this.weightFlag = weightFlag;
    }

    public Integer getPercentageFlag() {
        return percentageFlag;
    }

    public void setPercentageFlag(Integer percentageFlag) {
        this.percentageFlag = percentageFlag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}