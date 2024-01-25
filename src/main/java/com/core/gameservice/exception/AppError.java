package com.core.gameservice.exception;

import org.springframework.http.HttpStatus;

public class AppError extends Exception {

    private final HttpStatus httpStatus;
    private final String message;

    public AppError(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public AppError(String message, Throwable cause, HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
