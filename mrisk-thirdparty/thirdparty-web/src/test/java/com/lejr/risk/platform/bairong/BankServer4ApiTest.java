package com.zoom.risk.platform.bairong;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.bfd.facade.MerchantServer;
import net.sf.json.JSONObject;

public class BankServer4ApiTest {
	private  MerchantServer ms = new MerchantServer();

	public String query(JSONObject json) throws Exception {
		String result = "";
		result = ms.getApiData(json.toString(),"3000797");
		if (result.contains("code") ){
			if ( JSONObject.fromObject(result).getString("code").equals("100007")) {
				String tokenid = null;
			}
		}
		return result;
	}

	public String login(String userName,String password,String apiCode, String loginApiUrl) {
		String tokenId = "";
		try {
			String loginResult = ms.login(userName, password, loginApiUrl,apiCode);
			if( !StringUtils.isBlank(loginResult)){
				JSONObject json = JSONObject.fromObject(loginResult);
				if(json.containsKey("tokenid")){
					tokenId = json.getString("tokenid");
				}else{
					tokenId = null;
				}
			}else{
				tokenId = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tokenId;
	}

	public static void main(String[] args) throws Exception {
		/*
		 画像和幻影
		 参数格式:
		 	{
			    "apiName":"bankServerApi",
			    "tokenid":"wangyu_dx84ovsui3ps1eslz5k7oklc6",
			    "reqData":{
			        "meal":"****",
			        "id":"****",
			        "cell":[
			            "*****"
			        ],
			        "bank_id":"****",
			        "name":"",
			        "apply_source":"Counter_application"
			    }
			}
			
			cell、mail为数组3000779
		*/
		BankServer4ApiTest  test = new BankServer4ApiTest();
		String tokenId = test.login("homelink","homelink","3000797","SandboxLoginApi");
		if( tokenId != null ){
			JSONObject jso = new JSONObject();
			//
			JSONObject reqData = new JSONObject();
			jso.put("apiName", "SandboxApi");//config配置文件对应的url地址。
			jso.put("tokenid", tokenId);
			reqData.put("meal", "Execution");//模块名字，填写子产品代号。必填
			//身份证加密
			reqData.put("id", "140502198811102244");
			//电话号码加密
			reqData.put("cell", "13986671110");
			reqData.put("name","王亮");
			jso.put("reqData", reqData);
			try {
				String result = new BankServer4ApiTest().query(jso);
				if (result.contains("code") && JSONObject.fromObject(result).getString("code")
						.equals("100007")) {
					result = new BankServer4ApiTest().query(jso);
				}
				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
