package com.zoom.risk.operating.ruleconfig.model;

public class RiskNumber {
	public static final String ENTITY_CLASS_QUOTA = "Q";
	public static final String ENTITY_CLASS_POLICY = "P";
	public static final String ENTITY_CLASS_RULE = "R";
	public static final String ENTITY_CLASS_ROUTER = "T";

	public static final String SCARD_CLASS_PARAM = "SP";
	public static final String SCARD_CLASS = "SC";
	public static final String SCARD_CLASS_ROUTER = "SR";

	private Long id;

	private String entityClass;

	private String sceneNo;

	private Integer seqNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass == null ? null : entityClass.trim();
	}

	public String getSceneNo() {
		return sceneNo;
	}

	public void setSceneNo(String sceneNo) {
		this.sceneNo = sceneNo == null ? null : sceneNo.trim();
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String toString() {
		return "entityClass:" + entityClass + ", sceneNo:" + sceneNo + ", seqNo:" + seqNo;
	}
}