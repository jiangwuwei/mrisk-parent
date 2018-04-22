package com.zoom.risk.platform.scard.mode;

/**
 * @author jiangyulin
 *May 22, 2015
 */
public class SCardParamVo {
    private Long id;
    private Long scardId;
    private String paramNo;
    private String chineseName;
    private String paramName;
    private Float defaultScore;
    private Float weightValue;
    private Float finalValue;

    private SCardParamRouteVo executeRoute;

    public SCardParamRouteVo getExecuteRoute() {
        return executeRoute;
    }

    public void setExecuteRoute(SCardParamRouteVo executeRoute) {
        this.executeRoute = executeRoute;
    }

    public String getParamNo() {
        return paramNo;
    }

    public void setParamNo(String paramNo) {
        this.paramNo = paramNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScardId() {
        return scardId;
    }

    public void setScardId(Long scardId) {
        this.scardId = scardId;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public Float getDefaultScore() {
        return defaultScore;
    }

    public void setDefaultScore(Float defaultScore) {
        this.defaultScore = defaultScore;
    }

    public Float getWeightValue() {
        return weightValue;
    }

    public void setWeightValue(Float weightValue) {
        this.weightValue = weightValue;
    }

    public Float getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(Float finalValue) {
        this.finalValue = finalValue;
    }

    @Override
    public String toString() {
        return "SCardParamVo{" +
                "id=" + id +
                ", scardId=" + scardId +
                ", paramNo='" + paramNo + '\'' +
                ", chineseName='" + chineseName + '\'' +
                ", paramName='" + paramName + '\'' +
                ", defaultScore=" + defaultScore +
                ", weightValue=" + weightValue +
                ", finalValue=" + finalValue +
                ", executeRoute=" + executeRoute +
                '}';
    }
}
