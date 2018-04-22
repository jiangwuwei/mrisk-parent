package com.zoom.risk.gateway.fraud.utils;

import java.util.UUID;

public class RiskUUIDFactory {
	
	public static String generateUUID(String scene){
		return scene + "_" +UUID.randomUUID().toString().replaceAll("-", "");
	}
}
