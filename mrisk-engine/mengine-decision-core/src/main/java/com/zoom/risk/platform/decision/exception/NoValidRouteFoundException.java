package com.zoom.risk.platform.decision.exception;

/**
 * Created by jiangyulin on 2017/5/18.
 */
public class NoValidRouteFoundException extends RuntimeException {
    public NoValidRouteFoundException() {
        super();
    }

    public NoValidRouteFoundException(String message) {
        super(message);
    }

    public NoValidRouteFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoValidRouteFoundException(Throwable cause) {
        super(cause);
    }

    protected NoValidRouteFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
