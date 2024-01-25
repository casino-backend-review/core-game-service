package com.core.gameservice.services;

import com.core.gameservice.dto.*;

import java.util.List;

public interface AgentGameService {
    List<AgentGameResponse> createAgentGame(CreateAgentGameRequest request) ;
    List<AgentGameResponse> updateAgentGame(UpdateAgentGameRequest request) ;
    AgentGameResponse getAgentGameDetails(GetAgentGameDetailsRequest request);
    List<AgentGameResponse> getAgentGame(String username) ;
    void deleteAgentGame(String username);

     List<AgentGameResponse> getAgentGameByUpline(String uplineUsername,String productId);

    List<AgentGameResponse> updateAgentGameList(List<UpdateAgentGameByProductRequest> request);

    List<AgentGameResponse> updateAgentGameMemberStatus(UpdateAgentGameMemberStatusRequest request);
}
