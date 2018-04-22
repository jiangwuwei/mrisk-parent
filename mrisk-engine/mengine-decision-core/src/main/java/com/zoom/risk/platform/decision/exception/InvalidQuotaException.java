package com.zoom.risk.platform.decision.exception;

/**
 * Created by jiangyulin on 2017/5/18.
 */
public class InvalidQuotaException extends RuntimeException {
    public InvalidQuotaException() {
        super();
    }

    public InvalidQuotaException(String message) {
        super(message);
    }

    public InvalidQuotaException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidQuotaException(Throwable cause) {
        super(cause);
    }

    protected InvalidQuotaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
