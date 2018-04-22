package com.zoom.risk.platform.decision.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jiangyulin on 2017/5/10.
 */
public class Node implements Serializable{
    public static final int NODE_TYPE_ROOT =1;
    public static final int NODE_TYPE_BRANCH =2;
    public static final int NODE_TYPE_LEAF =3;

    protected Long id;
    protected Long parentId;
    protected Long policyId;
    protected String sceneNo;
    protected String nodeNo;
    protected Integer nodeType;

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


    public Long getParentId() {
        return parentId;
    }

    public String getNodeNo() {
        return nodeNo;
    }

    public void setNodeNo(String nodeNo) {
        this.nodeNo = nodeNo;
    }
}
