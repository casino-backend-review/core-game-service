package com.core.gameservice.repositories;

import com.core.gameservice.entity.AgentGame;
import com.core.gameservice.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgentGameRepository extends JpaRepository<AgentGame, Long> { // Assuming the ID type is Long

    // Custom query method to find AgentGames by username, status, and productId
    List<AgentGame> findAllByUsernameAndStatusAndProductId(String username, Status status, String productId);

    // Method to count AgentGames based on upline username
    long countByUsername(String upline);

    // Method to find all AgentGames by username
    List<AgentGame> findAllByUsername(String username);

    // Method to find a single AgentGame by username and productId
    Optional<AgentGame> findByUsernameAndProductId(String username, String productId);

    // Method to delete AgentGame by username
    void deleteByUsername(String username);

    List<AgentGame> findByUsername(String username);

    // Add other query methods as required
}
