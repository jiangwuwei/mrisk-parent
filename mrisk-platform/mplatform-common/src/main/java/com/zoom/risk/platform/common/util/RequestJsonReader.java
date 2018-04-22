package com.zoom.risk.platform.common.util;

import java.io.IOException;
import java.io.InputStream;

public class RequestJsonReader {

	public static String readRiskData(InputStream in) throws IOException {

		byte[] buffer = new byte[10*1024];
		StringBuffer sb = new StringBuffer("");
		int num = 0;
		while ((num = in.read(buffer)) > -1) {
			sb.append(new String(buffer, 0, num, "utf-8"));
		}
		in.close();
		return sb.toString();
	}

}
