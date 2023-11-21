package com.core.gameservice.dto;

public class PgGameResponse {

    private String gameId;
    private String gameName;
    private String gameStatus; // e.g., 'active', 'inactive', 'under maintenance'
    // Additional fields as required

    // Default constructor
    public PgGameResponse() {
    }

    // Constructor with parameters
    public PgGameResponse(String gameId, String gameName, String gameStatus) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameStatus = gameStatus;
    }

    // Getters and setters
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    // Additional getters and setters for other fields

    // Override toString(), equals(), and hashCode() methods if necessary
}
