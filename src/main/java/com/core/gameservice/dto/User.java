package com.core.gameservice.dto;


import com.core.gameservice.enums.UserStatus;
import com.core.gameservice.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private String subAccountUserPermissionAccess; // in case of subaccount user

    public User(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.accountUsername = user.getAccountUsername();
        this.token = user.getToken();
        this.mobile = user.getMobile();
        this.uCompany = user.getUCompany();
        this.uShareHolder = user.getUShareHolder();
        this.uSuperSenior = user.getUSuperSenior();
        this.uMaster = user.getUMaster();
        this.uSenior = user.getUSenior();
        this.uAgent = user.getUAgent();
        this.currency = user.getCurrency();
        this.type = user.getType();
        this.upline = user.getUpline();
        this.uplineType = user.getUplineType();
        this.parentId = user.getParentId();
        this.parentTopUsername = user.getParentTopUsername();
        this.refSale = user.getRefSale();
        this.password = user.getPassword();
        this.latestIp = user.getLatestIp();
        this.latestLoggedIn = user.getLatestLoggedIn();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
        this.note = user.getNote();
        this.userStatus = user.getUserStatus();
        this.isSubAccountUser = user.getIsSubAccountUser();
        this.subAccountUserPermissionAccess = user.getSubAccountUserPermissionAccess();
    }
}

