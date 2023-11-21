package com.core.gameservice.services;

import com.core.gameservice.dto.AgentGameResponse;
import com.core.gameservice.dto.CreateAgentGameRequest;
import com.core.gameservice.dto.GetAgentGameDetailsRequest;
import com.core.gameservice.dto.UpdateAgentGameRequest;
import com.core.gameservice.exceptions.CustomException;

import java.util.List;

public interface AgentGameService {
    List<AgentGameResponse> createAgentGame(CreateAgentGameRequest request) throws CustomException;
    List<AgentGameResponse> updateAgentGame(UpdateAgentGameRequest request) throws CustomException;
    AgentGameResponse getAgentGameDetails(GetAgentGameDetailsRequest request) throws CustomException;
    List<AgentGameResponse> getAgentGame(String username) throws CustomException;
    void deleteAgentGame(String username) throws CustomException;
    // Additional methods as needed
}
