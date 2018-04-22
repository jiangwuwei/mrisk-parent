package com.zoom.risk.platform.common.rpc;

import java.io.Serializable;

public class RpcResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_FAILED = 0;

    public static final int ERROR_SERVER_500 = 500;
    public static final int ERROR_PARAM_MISSING_400 = 400;
    public static final int ERROR_INVALID_PARAM_401 = 401;
    public static final int ERROR_NOT_FOUND_404 = 404;
    public static final int ERROR_SUCCESSS_200 = 200;

    private int resultCode;        // 1 SUCCESS 0 FAILED
    private int errorCode;
    private String message;
    private T data;


    public RpcResult() {
        this.resultCode = RESULT_SUCCESS;
        this.errorCode = ERROR_SUCCESSS_200;
    }

    public RpcResult(int resultCode, int errorCode) {
        this.resultCode = resultCode;
        this.errorCode = errorCode;
    }

    public RpcResult(int resultCode, int errorCode, String message) {
        this.resultCode = resultCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    public RpcResult(int resultCode, int errorCode, String message, T data) {
        this.resultCode = resultCode;
        this.errorCode = errorCode;
        this.message = message;
        this.data = data;
    }

    public RpcResult(int resultCode, T data) {
        this.resultCode = resultCode;
        this.data = data;
    }

    public RpcResult(int resultCode, int errorCode, T data) {
        this.resultCode = resultCode;
        this.errorCode = errorCode;
        this.data = data;
    }

    public boolean isOK() {
        return this.resultCode == RESULT_SUCCESS;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "RpcResult [resultCode=" + this.resultCode + ", errorCode=" + this.errorCode + ", message=" + this.message + ", data=" + this.data + "]";
    }
}

