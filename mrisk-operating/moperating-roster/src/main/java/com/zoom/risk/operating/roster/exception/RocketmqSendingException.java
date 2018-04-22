package com.zoom.risk.operating.roster.exception;

/**
 * Created by jiangyulin on 2015/4/11.
 */
public class RocketmqSendingException extends RuntimeException {
    public RocketmqSendingException() {
        super();
    }

    public RocketmqSendingException(String message) {
        super(message);
    }

    public RocketmqSendingException(String message, Throwable cause) {
        super(message, cause);
    }

    public RocketmqSendingException(Throwable cause) {
        super(cause);
    }

    protected RocketmqSendingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
