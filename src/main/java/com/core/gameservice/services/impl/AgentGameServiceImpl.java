package com.core.gameservice.services.impl;

import com.core.gameservice.dto.*;
import com.core.gameservice.entity.AgentGame;
import com.core.gameservice.entity.GameProvider;
import com.core.gameservice.enums.Status;
import com.core.gameservice.exceptions.CustomException;
import com.core.gameservice.repositories.AgentGameRepository;
import com.core.gameservice.repositories.GameProviderRepository;
import com.core.gameservice.services.AgentGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgentGameServiceImpl implements AgentGameService {

    private final AgentGameRepository agentGameRepository;
    private final GameProviderRepository gameProviderRepository;

    @Autowired
    public AgentGameServiceImpl(AgentGameRepository agentGameRepository,
                                GameProviderRepository gameProviderRepository) {
        this.agentGameRepository = agentGameRepository;
        this.gameProviderRepository = gameProviderRepository;
    }

    @Override
    public List<AgentGameResponse> createAgentGame(CreateAgentGameRequest request) throws CustomException {
        long gameUplineCount = agentGameRepository.countByUsername(request.getUpline());
        if (!request.getUserType().equals("company") &&gameUplineCount == 0) {
            throw new CustomException("Upline not found");
        }

        for (Product game : request.getProducts()) {
            if (game.isChecked()) {
                Optional<GameProvider> gameDetailOptional = gameProviderRepository.findByProductId(game.getProductId());
                if (gameDetailOptional.isEmpty()) {
                    throw new CustomException("Product not found");
                }
                GameProvider gameDetail = gameDetailOptional.get();

                if (!request.getUserType().equals("company") && !request.getUserType().equals("admin")) {
                    validateUplineRate(request.getUpline(), game.getProductId(), game.getRate());
                }

                AgentGame newAgentGame = new AgentGame();
                newAgentGame.setUsername(request.getUsername());
                newAgentGame.setUpline(request.getUpline());
                newAgentGame.setUserType(request.getUserType());
                newAgentGame.setProductId(game.getProductId());
                newAgentGame.setRate(game.getRate());
                newAgentGame.setRateLimit(gameDetail.getRate());
                newAgentGame.setProvider(gameDetail.getProvider());
                newAgentGame.setStatus(Status.A);
                newAgentGame.setCreatedAt(LocalDateTime.now());


                agentGameRepository.save(newAgentGame);
            }
        }

        List<AgentGame> agentGames = agentGameRepository.findByUsername(request.getUsername());
        return agentGames.stream()
                .map(this::convertToAgentGameResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AgentGameResponse> updateAgentGame(UpdateAgentGameRequest request) throws CustomException {
        List<AgentGame> userGames = agentGameRepository.findByUsername(request.getUsername());
        if (userGames.isEmpty()) {
            throw new CustomException("User not found");
        }

        for (Product game : request.getProduct())
            if (game.isChecked()) {
                Optional<GameProvider> gameDetailOptional = gameProviderRepository.findByProductId(game.getProductId());
                if (gameDetailOptional.isEmpty()) {
                    throw new CustomException("Product not found");
                }
                GameProvider gameDetail = gameDetailOptional.get();

                if (!request.getUserType().equals("company") && !request.getUserType().equals("admin")) {
                    validateUplineRate(request.getUpline(), game.getProductId(), game.getRate());
                }

                AgentGame agentGame = agentGameRepository.findByUsernameAndProductId(request.getUsername(), game.getProductId())
                        .orElseThrow(() -> new CustomException("Agent game with the product not found"));

                agentGame.setStatus(game.getNewGameStatus());
                agentGame.setRate(game.getNewRate());
                agentGame.setRateLimit(gameDetail.getRateLimit());
                agentGameRepository.save(agentGame);
            }

        List<AgentGame> updatedGames = agentGameRepository.findByUsername(request.getUsername());
        return updatedGames.stream()
                .map(agentGame -> convertToAgentGameResponse(agentGame))
                .collect(Collectors.toList());
    }

    @Override
    public AgentGameResponse getAgentGameDetails(GetAgentGameDetailsRequest request) throws CustomException {
        List<AgentGame> agentGames = agentGameRepository
                .findAllByUsernameAndStatusAndProductId(request.getAgentId(), Status.A, request.getGameId());

        if (agentGames.isEmpty()) {
            throw new CustomException("No agent games found for the given criteria");
        }

        return agentGames.stream()
                .map(agentGame -> {
                    Optional<GameProvider> gameDetailOptional = gameProviderRepository.findByProductId(agentGame.getProductId());
                    if (!gameDetailOptional.isPresent()) {
                        try {
                            throw new CustomException("Game details not found for product ID: " + agentGame.getProductId());
                        } catch (CustomException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return convertToAgentGameResponse(agentGame);
                })
                .findFirst()
                .orElseThrow(() -> new CustomException("No matching agent games found"));
    }

    private AgentGameResponse convertToAgentGameResponse(AgentGame agentGame) {

        return AgentGameResponse.builder().id(agentGame.getId()).username(agentGame.getUsername()).
                userType(agentGame.getUserType()).upline(agentGame.getUpline())
                .productId(agentGame.getProductId()).provider(agentGame.getProvider()).rate(agentGame.getRate()).
                rateLimit(agentGame.getRateLimit()).note(agentGame.getNote()).status(agentGame.getStatus()).build();
    }


    @Override
    public List<AgentGameResponse> getAgentGame(String username) throws CustomException {
        List<AgentGame> agentGames = agentGameRepository.findByUsername(username);
        if (agentGames.isEmpty()) {
            throw new CustomException("Agent games not found");
        }
        return agentGames.stream()
                .map(this::convertToAgentGameResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAgentGame(String username) throws CustomException {
        try {
            agentGameRepository.deleteByUsername(username);
        } catch (Exception e) {
            throw new CustomException("Error occurred while deleting agent games: " + e.getMessage());
        }
    }

    private void validateUplineRate(String upline, String productId, double rate) throws CustomException {
        Optional<AgentGame> uplineGameOpt = agentGameRepository.findByUsernameAndProductId(upline, productId);
        if (!uplineGameOpt.isPresent()) {
            throw new CustomException("Error getting game upline or product upline not allowed");
        }
        AgentGame uplineGame = uplineGameOpt.get();

        Optional<GameProvider> gameDetailOptional = gameProviderRepository.findByProductId(productId);
        if (!gameDetailOptional.isPresent()) {
            throw new CustomException("Error getting game details");
        }
        GameProvider gameDetail = gameDetailOptional.get();

        if (rate > uplineGame.getRate() || rate > gameDetail.getRate()) {
            throw new CustomException("Product error rate limit exceeded");
        }
    }

}
