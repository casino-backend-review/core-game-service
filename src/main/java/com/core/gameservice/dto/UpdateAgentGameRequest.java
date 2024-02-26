package com.core.gameservice.dto;

import com.core.gameservice.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAgentGameRequest {
    private String username;
    private UserType userType;
    private String upline;
    private List<Product> products;
    private List<String> downline;
    private UserAndDownlineHierarchyInfo userAndDownlineHierarchyInfo;
}