package com.core.gameservice.services;

import com.core.gameservice.dto.*;
import com.core.gameservice.exception.ApiException;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;

public interface AgentGameService {
    List<AgentGameResponse> createAgentGame(CreateAgentGameRequest request) throws ApiException;
    List<AgentGameResponse> updateAgentGame(UpdateAgentGameRequest request) throws ApiException;
    AgentGameResponse getAgentGameDetails(GetAgentGameDetailsRequest request) throws ApiException;
    List<AgentGameResponse> getAgentGame(String username) throws ApiException;
    void deleteAgentGame(String username) throws ApiException;

     List<AgentGameResponse> getAgentGameByUpline(String uplineUsername, String productId, Pageable pageable) throws ApiException;

    HashMap<String, List<AgentGameResponse>> updateAgentGameList(List<UpdateAgentGameByProductRequest> request, String token, BindingResult bindingResult) throws ApiException;

    List<AgentGameResponse> updateAgentGameMemberStatus(UpdateAgentGameMemberStatusRequest request, BindingResult bindingResult) throws ApiException;

    void deleteAgentGames(List<String> agentGameDeleteRequest) throws ApiException;
}
