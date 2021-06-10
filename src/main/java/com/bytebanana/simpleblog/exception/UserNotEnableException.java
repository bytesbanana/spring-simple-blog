package com.bytebanana.simpleblog.exception;

public class UserNotEnableException extends RuntimeException {
    public UserNotEnableException(String message) {
        super(message);
    }

    public UserNotEnableException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotEnableException(Throwable cause) {
        super(cause);
    }

    public UserNotEnableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
