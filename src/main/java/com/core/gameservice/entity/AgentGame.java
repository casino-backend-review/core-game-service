package com.core.gameservice.entity;

import com.core.gameservice.dto.BetLimit;
import com.core.gameservice.enums.Group;
import com.core.gameservice.enums.Status;
import com.core.gameservice.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

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

    private UserType userType;

    private Double rate;

    private Double rateLimit;

    private String productId;

    private String provider;
    private Map<Group, BetLimit> betLimitConfiguration; //other than member and admin
    private Double commission;
    private Double commissionRate;
    private String productName;

    private String category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    private Status status;

    private String note;

    private Status memberStatus;
}
