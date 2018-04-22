/**
 * 
 */
package com.zoom.risk.platform.engine.vo;

import java.util.Date;

/**
 * @author jiangyulin
 *Nov 12, 2015
 */
public abstract class AbstractBase {
	protected Long id;
	protected String name;
	protected String description;
	protected Date createdDate;
	protected Date modifiedDate;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedDate() {
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
	
}
