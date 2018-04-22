/**
 * 
 */
package com.zoom.risk.platform.scard.jsonvo;

import java.io.Serializable;

/**
 * @author jiangyulin
 *Nov 22, 2015
 */
public class ScoreCardParam implements Serializable {
	private static final long serialVersionUID = 2563200077814632027L;
	private String paramNo;
	private String chineseName;
	private String paramName;
	private Float defaultScore;
	private Float weightValue;
	//计算出来的最终值
	private Float finalScore;
	private ScoreCardParamRoute scoreCardParamRoute;

	public String getParamNo() {
		return paramNo;
	}

	public void setParamNo(String paramNo) {
		this.paramNo = paramNo;
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

	public ScoreCardParamRoute getScoreCardParamRoute() {
		return scoreCardParamRoute;
	}

	public void setScoreCardParamRoute(ScoreCardParamRoute scoreCardParamRoute) {
		this.scoreCardParamRoute = scoreCardParamRoute;
	}

	public Float getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(Float finalScore) {
		this.finalScore = finalScore;
	}

	@Override
	public String toString() {
		return "ScoreCardParam{" +
				"paramNo='" + paramNo + '\'' +
				", chineseName='" + chineseName + '\'' +
				", paramName='" + paramName + '\'' +
				", defaultScore=" + defaultScore +
				", weightValue=" + weightValue +
				", finalScore=" + finalScore +
				", scoreCardParamRoute=" + scoreCardParamRoute +
				'}';
	}
}
