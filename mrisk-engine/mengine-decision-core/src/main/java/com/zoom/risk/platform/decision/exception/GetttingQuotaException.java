package com.zoom.risk.platform.decision.exception;

/**
 * Created by jiangyulin on 2017/5/18.
 */
public class GetttingQuotaException extends RuntimeException {
    public GetttingQuotaException() {
        super();
    }

    public GetttingQuotaException(String message) {
        super(message);
    }

    public GetttingQuotaException(String message, Throwable cause) {
        super(message, cause);
    }

    public GetttingQuotaException(Throwable cause) {
        super(cause);
    }

    protected GetttingQuotaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
