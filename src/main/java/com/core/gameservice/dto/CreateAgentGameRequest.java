package com.core.gameservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAgentGameRequest {
    private String username;
    private String userType;
    private String upline;
    private List<Product> products; // Ensure this matches with your getter method
}
