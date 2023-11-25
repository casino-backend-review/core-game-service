package com.core.gameservice.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAgentGameRequest {

    private String agentId; // Identifier for the agent
    private String gameId; // Identifier for the game
    // Fields representing the details that can be updated
    private String newGameStatus; // Example field
    private Double newRate; // Another example field, if rate updates are relevant
    @Getter
    private String username;
    @Getter
    private String getProducts;
    private Object getUserType;
    private String getUpline;

    private String userType;
    private String upline;
    private List<Product> products; // Ensure this matches with your getter method
    private List<String> downline;


    public Object getUserType() {
        return this.getUserType;
    }

    public String getUpline() {
        return this.getUpline;
    }

    public String getProducts() {
        return this.getProducts;
    }
}


