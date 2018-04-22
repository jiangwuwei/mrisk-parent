package com.zoom.risk.operating.decision.exception;

/**
 * Created by jiangyulin on 2015/5/18.
 */
public class InvalidNodeException extends RuntimeException {
    public InvalidNodeException() {
        super();
    }

    public InvalidNodeException(String message) {
        super(message);
    }

    public InvalidNodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidNodeException(Throwable cause) {
        super(cause);
    }

    protected InvalidNodeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
