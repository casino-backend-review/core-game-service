package com.core.gameservice.client;


import com.core.gameservice.dto.UserAndDownlineHierarchyInfo;
import com.core.gameservice.enums.UserType;
import com.core.gameservice.exception.ApiException;
import com.core.gameservice.exception.ApiResponseMessage;

import java.util.List;

public interface MemberClient {

    ApiResponseMessage<List<UserAndDownlineHierarchyInfo>> getDownline(String uplineUsername, UserType userType, String token) throws ApiException;
    ApiResponseMessage<UserAndDownlineHierarchyInfo> findUserAndDownlineHierarchyInfo(String username, UserType userType, String token) throws ApiException;
}
