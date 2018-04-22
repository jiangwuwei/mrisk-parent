package com.zoom.risk.platform.decision.exception;

/**
 * Created by jiangyulin on 2017/5/18.
 */
public class MvelExecutionException extends RuntimeException {
    public MvelExecutionException() {
        super();
    }

    public MvelExecutionException(String message) {
        super(message);
    }

    public MvelExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MvelExecutionException(Throwable cause) {
        super(cause);
    }

    protected MvelExecutionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
