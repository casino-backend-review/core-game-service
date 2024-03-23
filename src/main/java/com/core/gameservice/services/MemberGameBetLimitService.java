package com.core.gameservice.services;

import com.core.gameservice.dto.MemberBetLimitDetails;
import com.core.gameservice.dto.MemberBetLimitRequest;
import com.core.gameservice.exception.ApiException;

import java.util.List;

public interface MemberGameBetLimitService {
    List<MemberBetLimitDetails> createGameMemberBetLimit(MemberBetLimitRequest request) throws ApiException;

    List<MemberBetLimitDetails> updateGameMemberBetLimit(MemberBetLimitRequest request) throws ApiException;

    List<MemberBetLimitDetails> getMemberGameBetLimit(String username) throws ApiException;

    void deleteMemberBetLimits(String username) throws ApiException;
}
