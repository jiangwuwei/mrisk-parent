package com.zoom.risk.operating.ruleconfig.model;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Scenes {

    public static final int SCENE_TYPE_ANTIFRAUD = 1;
    public static final int SCENE_TYPE_DECISION_TREE = 2;
    public static final int SCENE_TYPE_SCORE_CARD = 3;

    private String sceneNo;

    private String name;

    private String description;

    private Integer sceneType;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    private Date modifiedDate;

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
 // 方便前端展示
 	public String getCreatedDateStr() {
 		return DateFormatUtils.format(createdDate, "yyyy-MM-dd HH:mm:ss");
 	}

    public Integer getSceneType() {
        return sceneType;
    }

    public void setSceneType(Integer sceneType) {
        this.sceneType = sceneType;
    }
}