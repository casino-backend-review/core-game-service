package com.core.gameservice.dto;

import java.util.List;

public class CreateAgentGameRequest {

    private String username;
    private String upline;
    private String userType;
    private List<ProductRate> products;

    // Default constructor for frameworks
    public CreateAgentGameRequest() {
    }

    // Getters and setters

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

    public List<ProductRate> getProducts() {
        return products;
    }

    public void setProducts(List<ProductRate> products) {
        this.products = products;
    }

    // Inner class to represent products and rates
    public static class ProductRate {
        private String productId;
        private Double rate;
        private boolean checked; // This might represent whether the product is selected/active

        // Default constructor
        public ProductRate() {
        }

        // Getters and setters
        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public Double getRate() {
            return rate;
        }

        public void setRate(Double rate) {
            this.rate = rate;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }
    }

    // Override toString(), equals(), and hashCode() if necessary
}
