package com.core.gameservice.entity;

import com.core.gameservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "agent_game")
public class AgentGame {
    @Id
    private String id;

    private String username;

    private String upline;

    private String userType;

    private double rate;

    private Double rateLimit;

    private String productId;

    private String provider;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    private Status status;

    private String note;

    private Status memberStatus;
}
