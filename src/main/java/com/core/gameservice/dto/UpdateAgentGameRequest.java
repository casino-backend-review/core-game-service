package com.core.gameservice.dto;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAgentGameRequest {
    private String username;
    private String userType;
    private String upline;
    private List<Product> product;
    private List<String> downline;
}