package com.core.gameservice.exceptions;

public class BadRequestException extends Exception {

    // Constructor that accepts a message
    public BadRequestException(String message) {
        super(message);
    }

    // You can also add other constructors as needed, such as one that accepts a cause
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
