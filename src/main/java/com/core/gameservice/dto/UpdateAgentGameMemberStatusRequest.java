package com.core.gameservice.dto;

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
public class UpdateAgentGameMemberStatusRequest {

    private String username;
    private UserType userType;
    private Map<String, Status> productMemberGameStatusMap;
}
