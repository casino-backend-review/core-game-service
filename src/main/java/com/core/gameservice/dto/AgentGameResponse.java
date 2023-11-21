package com.core.gameservice.dto;

public class AgentGameResponse {

    private Long id;
    private String username;
    private String upline;
    private String userType;
    private Double rate;
    private Double rateLimit;
    private String productId;
    private String provider;
    private String status;
    private String note;

    // Default constructor for frameworks
    public AgentGameResponse() {
    }

    // Constructor with all fields for convenience
    public AgentGameResponse(Long id, String username, String upline, String userType, Double rate, Double rateLimit, String productId, String provider, String status, String note) {
        this.id = id;
        this.username = username;
        this.upline = upline;
        this.userType = userType;
        this.rate = rate;
        this.rateLimit = rateLimit;
        this.productId = productId;
        this.provider = provider;
        this.status = status;
        this.note = note;
    }

    // Getters and setters for all fields
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUpline() {
        return upline;
    }

    public void setUpline(String upline) {
        this.upline = upline;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(Double rateLimit) {
        this.rateLimit = rateLimit;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // toString(), equals(), and hashCode() methods can be overridden as needed
}
