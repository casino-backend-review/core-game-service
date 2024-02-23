package com.core.gameservice.dto;

import com.core.gameservice.enums.Status;
import com.core.gameservice.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Status status;
    private String note;
    private Status memberStatus;

}
