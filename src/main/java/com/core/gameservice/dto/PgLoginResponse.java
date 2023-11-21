package com.core.gameservice.dto;

public class PgLoginResponse {

    private boolean success;
    private String message;
    private String authToken; // Authentication token if login is successful
    // Additional fields as needed, like user details

    // Default constructor
    public PgLoginResponse() {
    }

    // Constructor with parameters
    public PgLoginResponse(boolean success, String message, String authToken) {
        this.success = success;
        this.message = message;
        this.authToken = authToken;
    }

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    // Additional getters and setters for other fields

    // Override toString(), equals(), and hashCode() methods if necessary
}
