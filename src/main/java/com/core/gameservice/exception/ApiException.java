package com.core.gameservice.exception;

public class ApiException extends RuntimeException {

    public ApiException() {

    }

    public ApiException(String msg) {
        super(msg);
    }
}
