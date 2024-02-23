package com.core.gameservice.dto;

import com.core.gameservice.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAgentGameByProductRequest {
    private String username;
    private UserType userType;
    private String upline;
    private Product product;
    private Boolean isDownlineImpact; //checked
}