package com.core.gameservice.dto;


import com.core.gameservice.enums.UserStatus;
import com.core.gameservice.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    private String id;
    private String username;
    private String accountUsername;// in case of subaccount user
    private String token;
    private String mobile;
    private String uCompany;
    private String uShareHolder;
    private String uSuperSenior;
    private String uMaster;
    private String uSenior;
    private String uAgent;
    private UserType type;
    private String upline;
    private String currency;
    private UserType uplineType;
    private String parentId; //may be mongoid
    private String parentTopUsername;
    private String refSale;
    private String password;
    private String latestIp;
    private LocalDateTime latestLoggedIn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String note;
    private UserStatus userStatus;
    private Boolean isSubAccountUser; // in case of subaccount user
    private List<SubAccountUserLevelAccess> subAccountUserPermissionAccess;// in case of subaccount user

}

