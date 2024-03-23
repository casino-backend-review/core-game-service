package com.core.gameservice.services.impl;

import com.core.gameservice.dto.MemberBetLimitDetails;
import com.core.gameservice.dto.MemberBetLimitRequest;
import com.core.gameservice.entity.MemberBetLimit;
import com.core.gameservice.enums.Status;
import com.core.gameservice.exception.ApiException;
import com.core.gameservice.repositories.MemberBetLimitRepository;
import com.core.gameservice.services.MemberGameBetLimitService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberGameBetLimitServiceImpl implements MemberGameBetLimitService {
    private final MemberBetLimitRepository memberBetLimitRepository;

    public MemberGameBetLimitServiceImpl(MemberBetLimitRepository memberBetLimitRepository) {
        this.memberBetLimitRepository = memberBetLimitRepository;
    }


    @Override
    public List<MemberBetLimitDetails> createGameMemberBetLimit(MemberBetLimitRequest request) throws ApiException {

        List<MemberBetLimit> byUsernameAndUsertype = memberBetLimitRepository.findByUsernameAndUserType(request.getUsername(), request.getUserType());

        if(!byUsernameAndUsertype.isEmpty()){
            throw new ApiException(String.format("Member betlimit configuration entry for  user %s already present",request.getUsername()),1, HttpStatus.FORBIDDEN);
        }

        List<MemberBetLimit> memberBetLimiterList=new ArrayList<>();

        List<MemberBetLimit> memberBetLimits = request.getMemberBetLimit().stream().map(memberBetLimit -> MemberBetLimit.builder()
                .username(request.getUsername())
                .userType(request.getUserType())
                .upline(request.getUpline())
                .productId(memberBetLimit.getProductId())
                .productName(memberBetLimit.getProductName())
                .provider(memberBetLimit.getProvider())
                .category(memberBetLimit.getCategory())
                .group(memberBetLimit.getGroup())
                .betLimit(memberBetLimit.getBetLimit())
                .commission(memberBetLimit.getCommission())
                .commissionRate(memberBetLimit.getCommissionRate())
                .createdAt(LocalDateTime.now())
                .note(String.format("Member bet limit created for productId  %s", memberBetLimit.getProductId()))
                .status(Status.A)
                .build()).toList();
        List<MemberBetLimit> memberBetLimitsData=new ArrayList<>();
        if(!memberBetLimits.isEmpty()){
             memberBetLimitsData = memberBetLimitRepository.saveAll(memberBetLimits);
        }

        return  memberBetLimitsData.stream()
                .map(this::convertToMemberBetLimitResponse)
                .collect(Collectors
                        .toList());
    }

    private MemberBetLimitDetails convertToMemberBetLimitResponse(MemberBetLimit memberBetLimit) {

        return MemberBetLimitDetails.builder()
                .productId(memberBetLimit.getProductId())
                .productName(memberBetLimit.getProductName())
                .provider(memberBetLimit.getProvider())
                .category(memberBetLimit.getCategory())
                .group(memberBetLimit.getGroup())
                .betLimit(memberBetLimit.getBetLimit())
                .commission(memberBetLimit.getCommission())
                .commissionRate(memberBetLimit.getCommissionRate())
                .status(memberBetLimit.getStatus())
                .build();
    }

    @Override
    public List<MemberBetLimitDetails> updateGameMemberBetLimit(MemberBetLimitRequest request) throws ApiException {
        try {
            List<MemberBetLimit> byUsernameAndStatus = memberBetLimitRepository.findByUsernameAndStatus(request.getUsername(), Status.A);
            List<MemberBetLimit> memberBetLimiterList=new ArrayList<>();

            List<MemberBetLimit> memberBetLimits = request.getMemberBetLimit().stream().map(memberBetLimit ->{
                Optional<MemberBetLimit> betLimit = byUsernameAndStatus.stream().filter(memberLimitData -> memberLimitData.getProductId().equals(memberBetLimit.getProductId())).findFirst();

                if(betLimit.isPresent()) {
                    MemberBetLimit limit = betLimit.get();
                    limit.setGroup(memberBetLimit.getGroup());
                    limit.setBetLimit(memberBetLimit.getBetLimit());
                    limit.setCommission(memberBetLimit.getCommission());
                    limit.setCommissionRate(memberBetLimit.getCommissionRate());
                    limit.setUpdatedAt(LocalDateTime.now());
                    limit.setStatus(Status.A);
                    return limit;
                }else{
                    try {
                        throw new ApiException(String.format("Member not found for provided productId %s",memberBetLimit.getProductId()),1, HttpStatus.FORBIDDEN);
                    } catch (ApiException e) {
                        throw new RuntimeException(e.getMessage());
                    }
                }

            }).toList();

            List<MemberBetLimit> memberBetLimitsData=new ArrayList<>();
            if(!memberBetLimits.isEmpty()){
                memberBetLimitsData = memberBetLimitRepository.saveAll(memberBetLimiterList);
            }

            return memberBetLimitsData.stream().map(this::convertToMemberBetLimitResponse)
                    .collect(Collectors
                            .toList());
        }catch (Exception exception){
                throw new ApiException("Exception occur while updating member bet limit details  :"+exception.getMessage(),1, HttpStatus.FORBIDDEN);
            }
    }

    @Override
    public List<MemberBetLimitDetails> getMemberGameBetLimit(String username) throws ApiException {
        try {
            List<MemberBetLimit> byUsernameAndStatus = memberBetLimitRepository.findByUsernameAndStatus(username, Status.A);

            return byUsernameAndStatus.stream().map(this::convertToMemberBetLimitResponse)
                    .collect(Collectors
                            .toList());
        }
        catch (Exception exception){
            throw new ApiException("Exception occur while getting member bet limit details",1, HttpStatus.FORBIDDEN);
        }
    }

    @Override
    public void deleteMemberBetLimits(String username) throws ApiException {
        try{
            memberBetLimitRepository.deleteAllByUsername(username);
        } catch (Exception e) {
            throw new ApiException("Error occurred while deleting all specified member bet limits for products: " + e.getMessage(),1, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
