package com.core.gameservice.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "agent_game")
public class AgentGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String username;

    @Column(length = 100)
    private String upline;

    @Column(nullable = false, length = 100)
    private String userType;

    @Column(nullable = false)
    private Double rate;

    @Column(nullable = false)
    private Double rateLimit;

    @Column(nullable = false, length = 100)
    private String productId;

    @Column(length = 100)
    private String provider;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    @Column(length = 2)
    private Status status;

    @Column(length = 100)
    private String note;

    // Constructors, getters, setters, and other methods

    public enum Status {
        A, // Assuming 'A' stands for Active
        C  // Assuming 'C' stands for Closed
    }

    // Constructor, Getters, Setters, and other methods
}
