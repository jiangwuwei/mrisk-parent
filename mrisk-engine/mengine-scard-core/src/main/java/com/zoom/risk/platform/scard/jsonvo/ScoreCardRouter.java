/**
 * 
 */
package com.zoom.risk.platform.scard.jsonvo;

import java.io.Serializable;

/**
 * @author jiangyulin
 *Nov 22, 2015
 */
public class ScoreCardRouter implements Serializable {
	private static final long serialVersionUID = 2353200077344689027L;
	private String sceneNo;//场景号4位
	private String name;
	private String routerExpression;
	private String routerNo;

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

	public String getRouterExpression() {
		return routerExpression;
	}

	public void setRouterExpression(String routerExpression) {
		this.routerExpression = routerExpression;
	}

	public String getRouterNo() {
		return routerNo;
	}

	public void setRouterNo(String routerNo) {
		this.routerNo = routerNo;
	}

	@Override
	public String toString() {
		return "ScoreCardRouter{" +
				"sceneNo='" + sceneNo + '\'' +
				", name='" + name + '\'' +
				", routerExpression='" + routerExpression + '\'' +
				", routerNo='" + routerNo + '\'' +
				'}';
	}
}
