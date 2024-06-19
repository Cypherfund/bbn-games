package com.cypherfund.bbn.exception;

import lombok.Getter;

public class AppException extends RuntimeException {
    @Getter
    private int code = 0;
    public AppException(String message) {
        super(message);
    }
    public AppException(String message, int code) {
        super(message);
        this.code = code;
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
