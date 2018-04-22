package com.zoom.risk.platform.decision.po;

/**
 * Created by jiangyulin on 2017/5/17.
 */
public class DecisionTreeWrapper {
    private Long policyId;
    private String policyNo;
    private DBNode root;

    public DecisionTreeWrapper(){

    }

    public DecisionTreeWrapper(Long policyId, String policyNo, DBNode root){
        this.policyId = policyId;
        this.policyNo = policyNo;
        this.root = root;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(String policyNo) {
        this.policyNo = policyNo;
    }

    public DBNode getRoot() {
        return root;
    }

    public void setRoot(DBNode root) {
        this.root = root;
    }
}
