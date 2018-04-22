package com.zoom.risk.platform.scard.mode;

import java.util.*;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public class SCard {
    public static final int WEIGHT_YES_1 = 1;
    public static final int WEIGHT_NO_1 = 0;
    private Long id;
    private String name;
	private String sceneNo;                //场景号4位
	private Integer weightValue;            //评分卡执行权重
	private String scardNo;
	private Integer weightFlag;             //参数变量是否有权重  0 没有 1 有
	private Integer percentageFlag;         //是否是百分比  0 不是 1 是
	private Integer status;

	public SCard(){
        ruleSet = new HashSet<>();
        paramSet = new HashSet<>();
    }

	private Set<SCardParam> paramSet;

	private Set<SCardRule> ruleSet;

    public  Set<SCardRule> getRuleSet() {
        return ruleSet;
    }

    public void addSCardRule(SCardRule scardRule) {
        ruleSet.add(scardRule);
    }

    public Set<SCardParam> getParamSet() {
        return paramSet;
    }

    public void addSCardParam(SCardParam scardParam) {
        paramSet.add(scardParam);
    }

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

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "ScoreCard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sceneNo='" + sceneNo + '\'' +
                ", weightValue=" + weightValue +
                ", scardNo='" + scardNo + '\'' +
                ", weightFlag=" + weightFlag +
                ", percentageFlag=" + percentageFlag +
                ", status=" + status +
                ", paramSet=" + paramSet +
                ", ruleSet=" + ruleSet +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}