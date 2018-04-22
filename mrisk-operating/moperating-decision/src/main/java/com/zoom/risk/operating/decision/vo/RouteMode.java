/**
 * 
 */
package com.zoom.risk.operating.decision.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangyulin
 *May 9, 2017
 */
public class RouteMode implements RouteOper{
	private Long nodeId;
	private boolean isJoin;
	private List<Route> routes;
	
	public RouteMode(Route route){
		this(false);
		this.routes.add(route);
	}

	public RouteMode(){
		this(false);
	}
	
	public RouteMode(boolean isJoin){
		this.isJoin = isJoin;
		this.routes = new ArrayList<>();
	} 
	
	public boolean getIsJoin() {
		return isJoin;
	}
	public void setIsJoin(boolean isJoin) {
		this.isJoin = isJoin;
	}

	public List<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(List<Route> routes) {
		this.routes = routes;
	}

	public void addRoute(Route route){
		this.routes.add(route);
	}
	
	
	public static RouteMode andRoute(Route route1, Route route2){
		RouteMode mode = new RouteMode(true);
		mode.addRoute(route1);
		mode.addRoute(route2);
		return mode;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	@Override
	@JSONField(serialize=false)
	public String getMevlExpression() {
		StringBuilder builder = new StringBuilder(" ( ");
		int lenght = 1;
		if ( this.isJoin ){
			lenght = this.routes.size();
		}
		if ( routes != null && routes.size() > 0 ) {
			String route = routes.get(0).getMevlExpression();
			builder.append(route);
			if (lenght > 1) {
				builder.append(" && ");
				route = routes.get(1).getMevlExpression();
				builder.append(route);
			}
			builder.append(" ) ");
			return builder.toString();
		}else{
			return null;
		}
	}
}
	