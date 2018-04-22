package com.zoom.risk.operating.es.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EventOutputModel {
	private long totalSize;
	private List<Map<String,Object>> resultList;
	
	public EventOutputModel(){
		this.resultList = new ArrayList<>();
	}
	
	public EventOutputModel(long totalSize){
		this.totalSize = totalSize;
		this.resultList = new ArrayList<>();
	}
	public long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}
	public List<Map<String, Object>> getResultList() {
		return resultList;
	}
	
	public void addHit(Map<String,Object> hit){
		resultList.add(hit);
	}
	
	

}
