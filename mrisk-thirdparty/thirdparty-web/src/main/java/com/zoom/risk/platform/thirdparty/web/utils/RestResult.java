package com.zoom.risk.platform.thirdparty.web.utils;

/**
 * @author jiangyulin
 *Oct 27, 2015
 */
public class RestResult<T>{
    public static final String OK = "200";
    public static final String PARAM_ERROR = "400";
    public static final String SYSTEM_ERROR = "500";

    private T data;
    private boolean success;
    private String code;
    private String errorMessage;

    public RestResult(){
        success = true;
        code = OK;
    }

    public RestResult(String code, String errorMessage, boolean successful){
        this.code = code;
        this.errorMessage = errorMessage;
        this.success = successful;
    }


    public static RestResult paramMissing(String errorMessage){
        RestResult<Object> restResult = new RestResult<Object>();
        restResult.setSuccess(false);
        restResult.setCode(PARAM_ERROR);
        restResult.setErrorMessage(errorMessage);
        return restResult;
    }

    public static RestResult systemError(String errorMessage){
        RestResult<Object> restResult = paramMissing(errorMessage);
        restResult.setCode(SYSTEM_ERROR);
        return  restResult;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isOk(){
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
