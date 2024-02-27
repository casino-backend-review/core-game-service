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
@Document(collection = "game_providers")
public class GameProvider {

@Id
    private Long id;

    private String callbackUrl;

    private String productId;


    private String productName;


    private String category;

    private String provider;

    private Double rate;

    //@Column(name = "company_fix", nullable = false, length = 100)
    private String companyFix;

    private Status status;

    private String readme;

    private String note;

    private LocalDateTime updatedAt;

    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    private boolean isAllowConfig;
    private Double rateLimit;
    private String providerType;
}
