package com.core.gameservice.controllers;

import com.core.gameservice.dto.*;
import com.core.gameservice.exception.Error;
import com.core.gameservice.exception.ApiException;
import com.core.gameservice.exception.ApiResponseMessage;
import com.core.gameservice.services.AgentGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class AgentGameController {

    private final AgentGameService agentGameService;

    @Autowired
    public AgentGameController(AgentGameService agentGameService) {
        this.agentGameService = agentGameService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseMessage<List<AgentGameResponse>>> createAgentGame(@RequestBody CreateAgentGameRequest request) {
        try {
            List<AgentGameResponse> response = agentGameService.createAgentGame(request);
            return ResponseEntity.ok(ApiResponseMessage.<List<AgentGameResponse>>builder().data(response).build());
        } catch (ApiException exception) {
        return getFailureResponseEntity(exception);
    }
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponseMessage<List<AgentGameResponse>>> updateAgentGame(@RequestBody UpdateAgentGameRequest request) {
        try {
            List<AgentGameResponse> response = agentGameService.updateAgentGame(request);
            return ResponseEntity.ok(ApiResponseMessage.<List<AgentGameResponse>>builder().data(response).build());
        }catch (ApiException exception) {
            return getFailureResponseEntity(exception);
        }
    }
    @DeleteMapping("/delete/username/{username}")
    public ResponseEntity<ApiResponseMessage<String>> deleteAgentGame(@PathVariable String username) {
        try {
            agentGameService.deleteAgentGame(username);
            return ResponseEntity.ok(ApiResponseMessage.<String>builder().data("Agent games deleted successfully").build());
        }catch (ApiException exception) {
            return getFailureResponseEntity(exception);
        }
    }

    @DeleteMapping("/delete/agentGames")
    public ResponseEntity<ApiResponseMessage<String>> deleteAgentGames(@RequestParam List<String> ids ) {
        try {
            agentGameService.deleteAgentGames(ids);
            return ResponseEntity.ok(ApiResponseMessage.<String>builder().data("Specified Agent games deleted successfully").build());
        }catch (ApiException exception) {
            return getFailureResponseEntity(exception);
        }
    }
    @PutMapping("/update-product-game-member-status/by-username-usertype")
    public ResponseEntity<ApiResponseMessage<List<AgentGameResponse>>> updateAgentGameMemberStatus(@RequestBody UpdateAgentGameMemberStatusRequest request) {
        try {
            List<AgentGameResponse> response = agentGameService.updateAgentGameMemberStatus(request);
            return ResponseEntity.ok(ApiResponseMessage.<List<AgentGameResponse>>builder().data(response).build());
        } catch (ApiException exception) {
            return getFailureResponseEntity(exception);
        }
    }

    @PutMapping("/update-list-users-game-status-rate-limit/by-product")
    public ResponseEntity<ApiResponseMessage<List<AgentGameResponse>>> updateAgentGameListByProduct(@RequestBody List<UpdateAgentGameByProductRequest> request) {
        try {
            List<AgentGameResponse> response = agentGameService.updateAgentGameList(request);
            return ResponseEntity.ok(ApiResponseMessage.<List<AgentGameResponse>>builder().data(response).build());
        } catch (ApiException exception) {
            return getFailureResponseEntity(exception);
        }
    }

    @GetMapping("/get-game-details/by-username/{username}/by-productid/{productId}")
    public ResponseEntity<ApiResponseMessage<AgentGameResponse>> getAgentGameDetails(@PathVariable("username") String username, @PathVariable("productId") String productId) {
        try {
            GetAgentGameDetailsRequest request = new GetAgentGameDetailsRequest();
            request.setAgentId(username);
            request.setGameId(productId);
            AgentGameResponse response = agentGameService.getAgentGameDetails(request);
            return ResponseEntity.ok(ApiResponseMessage.<AgentGameResponse>builder().data(response).build());
        }catch (ApiException exception) {
            return getFailureResponseEntity(exception);
        }
    }

    private static ResponseEntity getFailureResponseEntity(ApiException exception) {
        HttpStatus status = exception.getStatus() != null ? exception.getStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        ApiResponseMessage apiResponseMessage = new ApiResponseMessage(null, Error.builder().code(exception.getErrorCode()).message(exception.getMessage()).build());
        return ResponseEntity.status(status).body(apiResponseMessage);
    }

    @GetMapping("/get-all-game/by-username/{username}")
    public ResponseEntity<ApiResponseMessage<List<AgentGameResponse>>> getAgentGames(@PathVariable String username) {
        try {
            List<AgentGameResponse> response = agentGameService.getAgentGame(username);
            return ResponseEntity.ok(ApiResponseMessage.<List<AgentGameResponse>>builder().data(response).build());
        } catch (ApiException exception) {
            return getFailureResponseEntity(exception);
        }
    }

    @GetMapping("/get-all-game/by-upline-username/{uplineUsername}/productid/{productId}")
    public ResponseEntity<ApiResponseMessage<List<AgentGameResponse>>> getAgentGamesByUpline(@PathVariable String uplineUsername,@PathVariable String productId) {
        try {
            List<AgentGameResponse> response = agentGameService.getAgentGameByUpline(uplineUsername,productId);
            return ResponseEntity.ok(ApiResponseMessage.<List<AgentGameResponse>>builder().data(response).build());
        }catch (ApiException exception) {
            return getFailureResponseEntity(exception);
        }
    }


}
