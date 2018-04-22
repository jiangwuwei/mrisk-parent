package com.zoom.risk.operating.decision.po;

import java.util.Date;

/**
 * Created by jiangyulin on 2015/5/10.
 */
public class Node {
    public static final int NODE_TYPE_ROOT =1;
    public static final int NODE_TYPE_BRANCH =2;
    public static final int NODE_TYPE_LEAF =3;

    protected Long id;
    protected Long parentId;
    protected Long policyId;
    protected String sceneNo;
    protected Integer nodeType;
    protected Date createdDate;
    protected Date modifiedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getSceneNo() {
        return sceneNo;
    }

    public void setSceneNo(String sceneNo) {
        this.sceneNo = sceneNo;
    }

    public Integer getNodeType() {
        return nodeType;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public void setNodeType(Integer nodeType) {
        this.nodeType = nodeType;
    }
}
