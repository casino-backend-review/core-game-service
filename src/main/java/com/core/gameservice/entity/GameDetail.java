package com.core.gameservice.entity;

public class GameDetail {
    private String productId;
    private double rateLimit;
    private String provider;
    private String status;

    // Default constructor
    public GameDetail() {
    }

    // Parameterized constructor for convenience
    public GameDetail(String productId, double rateLimit, String provider, String status) {
        this.productId = productId;
        this.rateLimit = rateLimit;
        this.provider = provider;
        this.status = status;
    }

    // Getters and setters for each field
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(double rateLimit) {
        this.rateLimit = rateLimit;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Additional methods, such as equals, hashCode, and toString, could be added as needed
}
