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
public class MemberBetLimitRequest {
    private String username;
    private UserType userType;
    private String upline;
    private List<MemberBetLimit>  memberBetLimit;
}
