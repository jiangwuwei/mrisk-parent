package com.zoom.risk.gateway.common.utils;

import java.util.UUID;

public class RiskUUIDFactory {
	
	public static String generateUUID(String scene){
		return scene + "_" +UUID.randomUUID().toString().replaceAll("-", "");
	}
}
