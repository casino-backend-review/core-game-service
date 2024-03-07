package com.core.gameservice.dto;

import com.core.gameservice.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAgentGameByProductRequest {
    @NotBlank(message = "username cannot be null or blank")
    private String username;
    @NotBlank(message = "userType cannot be null")
    private UserType userType;
    @NotBlank(message = "upline cannot be null or blank")
    private String upline;
    @NotBlank(message = "product cannot be null")
    private Product product;
    @NotBlank(message = "isDownlineImpact  can be true or false")
    private Boolean isDownlineImpact; //checked
}