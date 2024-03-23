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
@Document(collection = "member-bet-limit")
public class MemberBetLimit {

    @Id
    private String id;

    private String username;

    private String upline;

    private UserType userType;

    private String productId;
    private String productName;


    private String provider;
    private Group group;
    private BetLimit betLimit; //only member
    private Double commission;
    private Double commissionRate;

    private String category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    private Status status;

    private String note;
}
