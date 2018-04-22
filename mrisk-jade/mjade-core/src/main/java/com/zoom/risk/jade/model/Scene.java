package com.zoom.risk.jade.model;

/**
 * 场景模型
 * @author jiangyulin
 *
 */
public class Scene {
	private long id;
	private String sceneName;
	private String sceneNo;
	private String accessSource;
	private String tableName;
	private String validGatewayCode;
 
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSceneName() {
		return sceneName;
	}
	public void setSceneName(String sceneName) {
		this.sceneName = sceneName;
	}
	public String getSceneNo() {
		return sceneNo;
	}
	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo;
	}
	public String getAccessSource() {
		return accessSource;
	}
	public void setAccessSource(String accessSource) {
		this.accessSource = accessSource;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((sceneNo == null) ? 0 : sceneNo.hashCode());
		return result;
	}
	
	public String getValidGatewayCode() {
		return validGatewayCode;
	}
	public void setValidGatewayCode(String validGatewayCode) {
		this.validGatewayCode = validGatewayCode;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Scene other = (Scene) obj;
		if (sceneNo == null) {
			if (other.sceneNo != null)
				return false;
		} else if (!sceneNo.equals(other.sceneNo))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Scene [id=" + id + ", sceneName=" + sceneName + ", sceneNo=" + sceneNo + ", accessSource="
				+ accessSource + ", tableName=" + tableName + ", gatewayCode=" + validGatewayCode + "]";
	}
	
}
