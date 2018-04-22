package com.zoom.risk.operating.scard.model;

import com.google.gson.Gson;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
public class SCardPolicies {
    private String sceneNo;

    private String name;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdDate;

    private Date modifiedDate;

    // *************前端展示*************//
    private List<Pair<String, String>> sceneConfigList;
    private boolean hasPolicyRouter;

    public String getSceneConfigString() {
        return new Gson().toJson(this.sceneConfigList);
    }

    public List<Pair<String, String>> getSceneConfigList() {
        return sceneConfigList;
    }

    public void setSceneConfigList(List<Pair<String, String>> sceneConfigList) {
        this.sceneConfigList = sceneConfigList;
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
    public String getCreatedDateStr() {
        return DateFormatUtils.format(createdDate, "yyyy-MM-dd HH:mm:ss");
    }

    public boolean isHasPolicyRouter() {
        return hasPolicyRouter;
    }

    public void setHasPolicyRouter(boolean hasPolicyRouter) {
        this.hasPolicyRouter = hasPolicyRouter;
    }
}