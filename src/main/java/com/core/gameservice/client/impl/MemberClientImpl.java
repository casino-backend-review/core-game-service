package com.core.gameservice.client.impl;


import com.core.gameservice.client.MemberClient;
import com.core.gameservice.dto.UserAndDownlineHierarchyInfo;
import com.core.gameservice.enums.UserType;
import com.core.gameservice.exception.ApiException;
import com.core.gameservice.exception.ApiResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class MemberClientImpl implements MemberClient {


    private final WebClient webClient;

    public MemberClientImpl(@Value("${services.memberService.url}")
                            String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl) // Replace with the actual API endpoint
                .build();
    }

    @Override
    public ApiResponseMessage<List<UserAndDownlineHierarchyInfo>> getDownline(String uplineUsername, UserType userType, String token) throws ApiException {
        try {

            ResponseEntity<ApiResponseMessage<List<UserAndDownlineHierarchyInfo>>> block = webClient.get()
                    .uri("/user/allDownline/uplineusername/{upline}/usertype/{userType}", uplineUsername, userType)
                    .header("Authorization", "Bearer " + token)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<ApiResponseMessage<List<UserAndDownlineHierarchyInfo>>>() {
                    })
                    .block();

            if (block != null && block.getBody() != null && block.getBody().getError() == null) {

                return block.getBody();
            } else {
                throw new ApiException("member service Error: " + Objects.requireNonNull(Objects.requireNonNull(block.getBody()).getError().getMessage()), block.getBody().getError().getCode(), HttpStatus.FORBIDDEN);

            }
        } catch (WebClientResponseException | ApiException exception) {
            throw new ApiException("member service Error: " + exception.getMessage(), 1, HttpStatus.FORBIDDEN);
        }
    }


}
