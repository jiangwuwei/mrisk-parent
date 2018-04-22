/**
 * 
 */
package com.zoom.risk.platform.engine.service;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * @author jiangyulin
 *Nov 17, 2015
 */
public class PolicyTargetStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipField(FieldAttributes f) {
		if ( f.getDeclaredClass().getName().startsWith("org.mvel2") ){
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}
