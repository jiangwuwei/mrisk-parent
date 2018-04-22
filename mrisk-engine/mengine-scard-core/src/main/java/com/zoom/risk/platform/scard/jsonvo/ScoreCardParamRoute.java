/**
 * 
 */
package com.zoom.risk.platform.scard.jsonvo;

import java.io.Serializable;

/**
 * @author jiangyulin
 *Nov 22, 2015
 */
public class ScoreCardParamRoute implements Serializable {
	private static final long serialVersionUID = 2563200077814689027L;
	private String paramName;
	private String routeName;
	private String routeExpression;
	private Float routeScore;

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getRouteExpression() {
		return routeExpression;
	}

	public void setRouteExpression(String routeExpression) {
		this.routeExpression = routeExpression;
	}

	public Float getRouteScore() {
		return routeScore;
	}

	public void setRouteScore(Float routeScore) {
		this.routeScore = routeScore;
	}

	@Override
	public String toString() {
		return "ScoreCardParamRoute{" +
				"paramName='" + paramName + '\'' +
				", routeName='" + routeName + '\'' +
				", routeExpression='" + routeExpression + '\'' +
				", routeScore=" + routeScore +
				'}';
	}
}
