/**
 * 
 */
package com.zoom.risk.jade.dao;

import java.util.Map;

/**
 * @author jiangyulin
 *Oct 9, 2015
 */
public interface AbstractMapper {
	public void singleInsertMap(Map<String,Object> map);
	public void singleUpdateMap(Map<String,Object> map);
}
