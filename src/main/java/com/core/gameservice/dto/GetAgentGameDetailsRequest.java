package com.core.gameservice.dto;

public class GetAgentGameDetailsRequest {

    private String agentId; // Assuming an agent ID is needed to identify the game details
    private String gameId; // Assuming a game ID is also required

    // Default constructor
    public GetAgentGameDetailsRequest() {
    }

    // Constructor with parameters
    public GetAgentGameDetailsRequest(String agentId, String gameId) {
        this.agentId = agentId;
        this.gameId = gameId;
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

    // Override toString(), equals(), and hashCode() methods if necessary
}
