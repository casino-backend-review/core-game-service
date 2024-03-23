package com.core.gameservice.controllers;

import com.core.gameservice.dto.*;
import com.core.gameservice.exception.ApiException;
import com.core.gameservice.exception.ApiResponseMessage;
import com.core.gameservice.exception.Error;
import com.core.gameservice.services.MemberGameBetLimitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game/member/betlimit")
public class MemberGameBetLimitController {

   private final MemberGameBetLimitService memberBetLimitService;

    public MemberGameBetLimitController(MemberGameBetLimitService memberBetLimitService) {
        this.memberBetLimitService = memberBetLimitService;
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponseMessage<List<MemberBetLimitDetails>>> createMemberBetLimit(@RequestBody MemberBetLimitRequest request) {
        try {
            List<MemberBetLimitDetails> response = memberBetLimitService.createGameMemberBetLimit(request);
            return ResponseEntity.ok(ApiResponseMessage.<List<MemberBetLimitDetails>>builder().data(response).build());
        } catch (ApiException exception) {
            return getFailureResponseEntity(exception);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponseMessage<List<MemberBetLimitDetails>>> updateAgentGame(@RequestBody MemberBetLimitRequest  request) {
        try {
            List<MemberBetLimitDetails> response = memberBetLimitService.updateGameMemberBetLimit(request);
            return ResponseEntity.ok(ApiResponseMessage.<List<MemberBetLimitDetails>>builder().data(response).build());
        }catch (ApiException exception) {
            return getFailureResponseEntity(exception);
        }
    }

    @GetMapping("/get-all-game/by-username/{username}")
    public ResponseEntity<ApiResponseMessage<List<MemberBetLimitDetails>>> getMemberGameBetLimit(@PathVariable(required = true) String username) {
        try {
            List<MemberBetLimitDetails> response = memberBetLimitService.getMemberGameBetLimit(username);
            return ResponseEntity.ok(ApiResponseMessage.<List<MemberBetLimitDetails>>builder().data(response).build());
        } catch (ApiException exception) {
            return getFailureResponseEntity(exception);
        }
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<ApiResponseMessage<String>> deleteMemberBetLimits(@RequestParam(required = true)String username) {
        try {
            memberBetLimitService.deleteMemberBetLimits(username);
            return ResponseEntity.ok(ApiResponseMessage.<String>builder().data("Specified Member bet limit for products ids deleted successfully").build());
        }catch (ApiException exception) {
            return getFailureResponseEntity(exception);
        }
    }

    private static ResponseEntity getFailureResponseEntity(ApiException exception) {
        HttpStatus status = exception.getStatus() != null ? exception.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage(null, Error.builder().code(exception.getErrorCode()).message(exception.getMessage()).build());
        return ResponseEntity.status(status).body(apiResponseMessage);
    }
}
