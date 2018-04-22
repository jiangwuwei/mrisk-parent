package com.zoom.risk.platform.decision.exception;

/**
 * Created by jiangyulin on 2017/5/24.
 */
public class QuotaParamException  extends RuntimeException {
    public QuotaParamException() {
        super();
    }

    public QuotaParamException(String message) {
        super(message);
    }

    public QuotaParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuotaParamException(Throwable cause) {
        super(cause);
    }

    protected QuotaParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
