/**
 * 
 */
package com.zoom.risk.platform.scard.jsonvo;

import java.io.Serializable;

/**
 * @author jiangyulin
 *Nov 22, 2015
 */
public class ScoreCardRuleRoute implements Serializable {
	private static final long serialVersionUID = 2563202177814689027L;
	private String routeName;
	private String routeExpression;
	private String finalResult;

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

	public String getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(String finalResult) {
		this.finalResult = finalResult;
	}

	@Override
	public String toString() {
		return "ScoreCardRuleRoute{" +
				"routeName='" + routeName + '\'' +
				", routeExpression='" + routeExpression + '\'' +
				", finalResult='" + finalResult + '\'' +
				'}';
	}
}
