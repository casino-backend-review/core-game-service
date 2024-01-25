package com.core.gameservice.dto;

import com.core.gameservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAgentGameMemberStatusRequest {

    private String username;
    private String userType;
    private Map<String, Status> productMemberGameStatusMap;
}
