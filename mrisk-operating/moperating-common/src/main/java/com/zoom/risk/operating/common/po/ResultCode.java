package com.zoom.risk.operating.common.po;

/**
 * @author jiangyulin, Mar, 17, 2015
 */
public class ResultCode {
	private int code;
	private String msg;
	
	public ResultCode(int code, String msg){
		this.code = code;
		this.msg = msg;
	}
	
	public static final ResultCode SUCCESS = new ResultCode(0, "操作成功");
	public static final ResultCode FAILED = new ResultCode(-1, "操作失败");
	public static final ResultCode DB_ERROR = new ResultCode(-2, "数据库操作失败，请检查操作是否得当或查看日志");
	public static final ResultCode ILLEAGE_PARAMS = new ResultCode(-3, "参数非法");
	public static final ResultCode NO_DATA = new ResultCode(-4, "没有相应的数据");
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
