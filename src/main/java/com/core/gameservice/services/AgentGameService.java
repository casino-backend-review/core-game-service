package com.core.gameservice.services;

import com.core.gameservice.dto.*;
import com.core.gameservice.exceptions.CustomException;

import java.util.List;

public interface AgentGameService {
    List<AgentGameResponse> createAgentGame(CreateAgentGameRequest request) throws CustomException;
    List<AgentGameResponse> updateAgentGame(UpdateAgentGameRequest request) throws CustomException;
    AgentGameResponse getAgentGameDetails(GetAgentGameDetailsRequest request) throws CustomException;
    List<AgentGameResponse> getAgentGame(String username) throws CustomException;
    void deleteAgentGame(String username) throws CustomException;

    List<AgentGameResponse> getAgentGameByUpline(String uplineUsername) throws CustomException;

    List<AgentGameResponse> updateAgentGameList(List<UpdateAgentGameByProductRequest> request) throws CustomException;
    // Additional methods as needed
}
