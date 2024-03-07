package com.core.gameservice.dto;

import com.core.gameservice.enums.Status;
import com.core.gameservice.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAgentGameMemberStatusRequest {
    @NotBlank(message = "username cannot be null or blank")
    private String username;
    @NotBlank(message = "userType cannot be null")
    private UserType userType;
    private Map<String, Status> productMemberGameStatusMap;
}
