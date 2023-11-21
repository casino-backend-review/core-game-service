package com.core.gameservice.dto;

public class UpdateAgentGameRequest {

    private String agentId; // Identifier for the agent
    private String gameId; // Identifier for the game
    // Fields representing the details that can be updated
    private String newGameStatus; // Example field
    private Double newRate; // Another example field, if rate updates are relevant
    // ... other fields as required

    // Default constructor
    public UpdateAgentGameRequest() {
    }

    // Constructor with parameters
    public UpdateAgentGameRequest(String agentId, String gameId, String newGameStatus, Double newRate) {
        this.agentId = agentId;
        this.gameId = gameId;
        this.newGameStatus = newGameStatus;
        this.newRate = newRate;
        // ... initialize other fields
    }

    // Getters and setters
    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getNewGameStatus() {
        return newGameStatus;
    }

    public void setNewGameStatus(String newGameStatus) {
        this.newGameStatus = newGameStatus;
    }

    public Double getNewRate() {
        return newRate;
    }

    public void setNewRate(Double newRate) {
        this.newRate = newRate;
    }

    // Additional getters and setters for other fields

    // Override toString(), equals(), and hashCode() methods if necessary
}
