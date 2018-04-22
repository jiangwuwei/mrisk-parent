/**
 * 
 */
package com.zoom.risk.operating.ruleconfig.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangyulin
 *May 9, 2017
 */
public class RouteMode implements RouteOper{

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

	public void removeSecondRoute(){
		if(routes != null && routes.size() == 2){
			routes.remove(1);
		}
	}
	
	public static RouteMode andRoute(Route route1, Route route2){
		RouteMode mode = new RouteMode(true);
		mode.addRoute(route1);
		mode.addRoute(route2);
		return mode;
	}

    /**
     * @return the selected attribute of the scene for the router
     */
	public String getSelectedAttribute(){
	    if( routes != null && !routes.isEmpty()){
	        return routes.get(0).getParamName();
        }
        return "";
    }

	@Override
	@JSONField(serialize=false)
	public String getMevlExpression() {
		StringBuilder builder = new StringBuilder(" ( ");
		if ( routes != null && routes.size() > 0 ) {
			String route = routes.get(0).getMevlExpression();
			builder.append(route);
			if (this.isJoin && routes.size() > 1) {
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
	