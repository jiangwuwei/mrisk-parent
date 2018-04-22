package com.zoom.risk.platform.common.rpc;

public class RpcResults {
    public static <D> RpcResult<D> success() {
        RpcResult<D> result = new RpcResult();
        result.setResultCode(RpcResult.RESULT_SUCCESS);
        return result;
    }

    public static <D> RpcResult<D> success(D data) {
        RpcResult<D> result = new RpcResult();
        result.setResultCode(RpcResult.RESULT_SUCCESS);
        result.setData(data);
        return result;
    }

    public static <D> RpcResult<D> failed(int errorCode) {
        RpcResult<D> result = new RpcResult();
        result.setResultCode(RpcResult.RESULT_FAILED);
        result.setErrorCode(errorCode);
        return result;
    }

    public static <D> RpcResult<D> paramMissing(String errMsg) {
        RpcResult<D> result = new RpcResult();
        result.setResultCode(RpcResult.RESULT_FAILED);
        result.setErrorCode(RpcResult.ERROR_PARAM_MISSING_400);
        result.setMessage(errMsg);
        return result;
    }

    public static <D> RpcResult<D> invalidParam(String errMsg) {
        RpcResult<D> result = new RpcResult();
        result.setResultCode(RpcResult.RESULT_FAILED);
        result.setErrorCode(RpcResult.ERROR_INVALID_PARAM_401);
        result.setMessage(errMsg);
        return result;
    }

    public static <D> RpcResult<D> systemError(String errMsg) {
        RpcResult<D> result = new RpcResult();
        result.setResultCode(RpcResult.RESULT_FAILED);
        result.setErrorCode(RpcResult.ERROR_SERVER_500);
        result.setMessage(errMsg);
        return result;
    }
}
