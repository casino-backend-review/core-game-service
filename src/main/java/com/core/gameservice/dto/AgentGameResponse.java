package com.core.gameservice.dto;

import com.core.gameservice.enums.Group;
import com.core.gameservice.enums.Status;
import com.core.gameservice.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgentGameResponse {

    private String id;
    private String username;
    private String upline;
    private UserType userType;
    private Double rate;
    private Double rateLimit;
    private String productId;
    private String provider;
    private String productName;
    private String category;
    private Map<Group, BetLimit> betLimitConfiguration; //other than member and admin
    private Double commission;
    private Double commissionRate;
    private Status status;
    private String note;
    private Status memberStatus;

}
