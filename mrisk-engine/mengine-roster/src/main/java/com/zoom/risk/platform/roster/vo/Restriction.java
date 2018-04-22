package com.zoom.risk.platform.roster.vo;

/**
 * Created by jiangyulin on 2017/4/5.
 */
public class Restriction {
    private Integer rosterType;
    private String sceneNo;

    public Restriction(){
    }

    public Restriction(Integer rosterType, String sceneNo){
        this.rosterType = rosterType;
        this.sceneNo = sceneNo;
    }

    public Integer getRosterType() {
        return rosterType;
    }

    public void setRosterType(Integer rosterType) {
        this.rosterType = rosterType;
    }

    public String getSceneNo() {
        return sceneNo;
    }

    public void setSceneNo(String sceneNo) {
        this.sceneNo = sceneNo;
    }
}
