/**
 * 
 */
package com.zoom.risk.platform.engine.vo;

import java.util.Date;

/**
 * @author jiangyulin
 *Nov 12, 2015
 */
public class PolicySet {
	private String sceneNo;   
	private String name;
	private String description;
	private Date createdDate;
	private Date modifiedDate;
	
	public Date getCreateDate() {
		return createdDate;
	}
	public void setCreateDate(Date createDate) {
		this.createdDate = createDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getSceneNo() {
		return sceneNo;
	}
	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
