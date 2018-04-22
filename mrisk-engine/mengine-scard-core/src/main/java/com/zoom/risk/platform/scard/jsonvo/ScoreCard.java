/**
 * 
 */
package com.zoom.risk.platform.scard.jsonvo;

import java.io.Serializable;

/**
 * @author jiangyulin
 *Nov 22, 2015
 */
public class ScoreCard implements Serializable {
	private static final long serialVersionUID = 2353200077814249007L;
	private Long id;
	private String name;
	private String sceneNo;                //场景号4位
	private String scardNo;
	private Integer weightFlag;             //参数变量是否有权重  0 没有 1 有
	private Integer percentageFlag;         //是否是百分比  0 不是 1 是

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ScoreCard{" +
				"name='" + name + '\'' +
				", sceneNo='" + sceneNo + '\'' +
				", scardNo='" + scardNo + '\'' +
				", weightFlag=" + weightFlag +
				", percentageFlag=" + percentageFlag +
				'}';
	}
}
