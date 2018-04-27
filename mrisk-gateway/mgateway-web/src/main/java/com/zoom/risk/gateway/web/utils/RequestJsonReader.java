package com.zoom.risk.gateway.web.utils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

public class RequestJsonReader {

	public static String readRiskData(HttpServletRequest request) throws IOException {
		byte[] buffer = new byte[10*1024];
		StringBuffer sb = new StringBuffer("");
		InputStream in = request.getInputStream();
		int num = 0;
		while ((num = in.read(buffer)) > -1) {
			sb.append(new String(buffer, 0, num, "utf-8"));
		}
		in.close();
		return sb.toString();
	}

}
