package com.core.gameservice.dto;

import java.util.List;

public class GameListResponse {

    private List<GameItem> games;
    private int totalGames;

    // Default constructor
    public GameListResponse() {
    }

    // Constructor with parameters
    public GameListResponse(List<GameItem> games, int totalGames) {
        this.games = games;
        this.totalGames = totalGames;
    }

    // Getters and setters
    public List<GameItem> getGames() {
        return games;
    }

    public void setGames(List<GameItem> games) {
        this.games = games;
    }

    public int getTotalGames() {
        return totalGames;
    }

    public void setTotalGames(int totalGames) {
        this.totalGames = totalGames;
    }

    // Inner class to represent each game item
    public static class GameItem {
        private String gameId;
        private String gameName;
        // other fields as needed

        // Default constructor
        public GameItem() {
        }

        // Getters and setters for each field
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

        // Additional getters and setters for other fields
    }

    // Override toString(), equals(), and hashCode() methods if necessary
}
