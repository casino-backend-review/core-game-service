package com.core.gameservice.exceptions;

public class CustomException extends Exception {

    // Constructor that accepts a message
    public CustomException(String message) {
        super(message); // Passes the message to the parent Exception class
    }

    // You can also add other constructors, methods, or fields if needed
}
