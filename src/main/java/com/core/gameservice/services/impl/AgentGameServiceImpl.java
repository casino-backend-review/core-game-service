package com.core.gameservice.services.impl;

import com.core.gameservice.dto.*;
import com.core.gameservice.entity.AgentGame;
import com.core.gameservice.entity.GameProvider;
import com.core.gameservice.enums.Status;
import com.core.gameservice.exception.ApiException;
import com.core.gameservice.repositories.AgentGameRepository;
import com.core.gameservice.repositories.GameProviderRepository;
import com.core.gameservice.services.AgentGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AgentGameServiceImpl implements AgentGameService {

    private final AgentGameRepository agentGameRepository;
    private final GameProviderRepository gameProviderRepository;

    private static final List<String> memberCreationPermissionUserType=Collections.unmodifiableList(List.of("agent","master","senior","super_senior"));

    @Autowired
    public AgentGameServiceImpl(AgentGameRepository agentGameRepository,
                                GameProviderRepository gameProviderRepository) {
        this.agentGameRepository = agentGameRepository;
        this.gameProviderRepository = gameProviderRepository;
    }

    @Override
    public List<AgentGameResponse> createAgentGame(CreateAgentGameRequest request) throws ApiException {
        long gameUplineCount = agentGameRepository.countByUsername(request.getUpline());
        if (!request.getUserType().equals("company") &&gameUplineCount == 0) {
            throw new ApiException("Upline not found",1, HttpStatus.NO_CONTENT);
        }

        for (Product game : request.getProducts()) {
            if (game.isChecked()) {
                Optional<GameProvider> gameDetailOptional = gameProviderRepository.findByProductId(game.getProductId());
                if (gameDetailOptional.isEmpty()) {
                    throw new ApiException("Product not found",1, HttpStatus.NO_CONTENT);
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
                newAgentGame.setRateLimit(game.getRateLimit());
                newAgentGame.setProvider(gameDetail.getProvider());
                newAgentGame.setStatus(Status.A);
                newAgentGame.setCreatedAt(LocalDateTime.now());

                if(memberCreationPermissionUserType.contains(request.getUserType())) {
                    newAgentGame.setMemberStatus(Status.A);
                }
                agentGameRepository.save(newAgentGame);
            }
        }

        List<AgentGame> agentGames = agentGameRepository.findByUsername(request.getUsername());
        return agentGames.stream()
                .map(this::convertToAgentGameResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AgentGameResponse> updateAgentGame(UpdateAgentGameRequest request) throws ApiException {
        List<AgentGame> userGames = agentGameRepository.findByUsername(request.getUsername());
        if (userGames.isEmpty()) {
            throw new ApiException("User not found",1, HttpStatus.NO_CONTENT);
        }

        for (Product game : request.getProduct()){
            processProduct(request.getUsername(),request.getUserType(),request.getUpline(), game);

        }

        List<AgentGame> updatedGames = agentGameRepository.findByUsername(request.getUsername());
        return updatedGames.stream()
                .map(this::convertToAgentGameResponse)
                .collect(Collectors.toList());
    }

    private void processProduct(String username,String userType,String upline, Product game) throws ApiException {
        if (game.isChecked()) {
            Optional<GameProvider> gameDetailOptional = gameProviderRepository.findByProductId(game.getProductId());
            if (gameDetailOptional.isEmpty()) {
                throw new ApiException("Product not found",1, HttpStatus.NO_CONTENT);
            }
            GameProvider gameDetail = gameDetailOptional.get();

            if (!userType.equals("company") && !userType.equals("admin")) {
                if(upline!=null&& game.getProductId()!=null&& game.getRate()!=null) {
                    validateUplineRate(upline, game.getProductId(), game.getRate());
                }
            }

            AgentGame agentGame = agentGameRepository.findByUsernameAndProductId(username, game.getProductId())
                    .orElseThrow(() -> new ApiException("Agent game with the product not found",1, HttpStatus.NO_CONTENT));

            if(game.getNewGameStatus()!=null) {
                agentGame.setStatus(game.getNewGameStatus());
            }
            if(game.getRate()!=null){
            agentGame.setRate(game.getRate());}
            if(game.getRateLimit()!=null){
            agentGame.setRateLimit(game.getRateLimit());}
            agentGameRepository.save(agentGame);
        }
    }

    @Override
    public AgentGameResponse getAgentGameDetails(GetAgentGameDetailsRequest request) throws ApiException {
        List<AgentGame> agentGames = agentGameRepository
                .findAllByUsernameAndStatusAndProductId(request.getAgentId(), Status.A, request.getGameId());

        if (agentGames.isEmpty()) {
            throw new ApiException("No agent games found for the given criteria",1, HttpStatus.NO_CONTENT);
        }

        return agentGames.stream()
                .map(agentGame -> {
                    Optional<GameProvider> gameDetailOptional = gameProviderRepository.findByProductId(agentGame.getProductId());
                    if (gameDetailOptional.isEmpty()) {
                        try {
                            throw new ApiException("Game details not found for product ID: " + agentGame.getProductId(),1, HttpStatus.NO_CONTENT);
                        } catch (ApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return convertToAgentGameResponse(agentGame);
                })
                .findFirst()
                .orElseThrow(() -> new ApiException("No matching agent games found",1, HttpStatus.NO_CONTENT));
    }

    private AgentGameResponse convertToAgentGameResponse(AgentGame agentGame) {

        return AgentGameResponse.builder().id(agentGame.getId()).username(agentGame.getUsername()).
                userType(agentGame.getUserType()).upline(agentGame.getUpline())
                .productId(agentGame.getProductId()).provider(agentGame.getProvider()).rate(agentGame.getRate()).
                rateLimit(agentGame.getRateLimit()).note(agentGame.getNote()).status(agentGame.getStatus()).memberStatus(agentGame.getMemberStatus()).build();
    }


    @Override
    public List<AgentGameResponse> getAgentGame(String username) throws ApiException {
        List<AgentGame> agentGames = agentGameRepository.findByUsername(username);
        if (agentGames.isEmpty()) {
            throw new ApiException("Agent games not found",1, HttpStatus.NO_CONTENT);
        }
        return agentGames.stream()
                .map(this::convertToAgentGameResponse)
                .collect(Collectors.toList());
    }

    @Override
   // @Transactional
    public void deleteAgentGame(String username) throws ApiException {
        try {
            agentGameRepository.deleteByUsername(username);
        } catch (Exception e) {
            throw new ApiException("Error occurred while deleting agent games: " + e.getMessage(),1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<AgentGameResponse> getAgentGameByUpline(String uplineUsername,String productId) throws ApiException {
        List<AgentGame> agentGames = agentGameRepository.findByUplineAndProductId(uplineUsername,productId);
        if (agentGames.isEmpty()) {
            throw new ApiException("Agent games not found",1, HttpStatus.NO_CONTENT);
        }
        return agentGames.stream()
                .map(this::convertToAgentGameResponse)
                .collect(Collectors.toList());    }

    @Override
    public List<AgentGameResponse> updateAgentGameList(List<UpdateAgentGameByProductRequest> request) throws ApiException {

        List<AgentGameResponse> agentGameResponses=new ArrayList<>();
        if(!CollectionUtils.isEmpty(request)){

            for(UpdateAgentGameByProductRequest updateAgentGameByProductRequest:request){
                List<AgentGame> userGames = agentGameRepository.findByUsername(updateAgentGameByProductRequest.getUsername());

                if (userGames.isEmpty()) {
                    throw new ApiException("User not found",1, HttpStatus.NO_CONTENT);
                }
                processProduct(updateAgentGameByProductRequest.getUsername(),updateAgentGameByProductRequest.getUserType(),updateAgentGameByProductRequest.getUpline(), updateAgentGameByProductRequest.getProduct());

                Optional<AgentGame> updatedGames = agentGameRepository.findByUsernameAndProductId(updateAgentGameByProductRequest.getUsername(),updateAgentGameByProductRequest.getProduct().getProductId());
                updatedGames.ifPresent(agentGame -> agentGameResponses.add(convertToAgentGameResponse(agentGame)));
            }
        }
        return agentGameResponses;
    }

    @Override
    public List<AgentGameResponse> updateAgentGameMemberStatus(UpdateAgentGameMemberStatusRequest request) throws ApiException {

        if(CollectionUtils.isEmpty(request.getProductMemberGameStatusMap())){
            throw new ApiException(" Product id member status map is empty or null",1, HttpStatus.NO_CONTENT);
        }
        List<AgentGameResponse> agentGameResponses=new ArrayList<>();

        List<AgentGame> allByUsernameAndUserType = agentGameRepository.findAllByUsernameAndUserType(request.getUsername(), request.getUserType());
        Map<String, Status> productMemberGameStatusMap = request.getProductMemberGameStatusMap();
           List<AgentGame> finalUpdatedAgentGame=new ArrayList<>();
        if (!CollectionUtils.isEmpty(allByUsernameAndUserType)&& !CollectionUtils.isEmpty(productMemberGameStatusMap)) {
            allByUsernameAndUserType.forEach(agentGame -> {
                if (agentGame != null && productMemberGameStatusMap.containsKey(agentGame.getProductId())) {
                    agentGame.setMemberStatus(productMemberGameStatusMap.get(agentGame.getProductId()));
                    finalUpdatedAgentGame.add(agentGame);
                }
            });
        }
        else{
            throw new ApiException("Agent games not found for userId: "+request.getUsername(),1, HttpStatus.NO_CONTENT);
        }
        List<AgentGame> agentGames = agentGameRepository.saveAll(finalUpdatedAgentGame);
      agentGames.stream().parallel().forEach(agentGame -> agentGameResponses.add(convertToAgentGameResponse(agentGame)));
        return agentGameResponses;
    }

    private void validateUplineRate(String upline, String productId, double rate) throws ApiException {
        Optional<AgentGame> uplineGameOpt = agentGameRepository.findByUsernameAndProductId(upline, productId);
        if (!uplineGameOpt.isPresent()) {
            throw new ApiException("Error getting game upline or product upline not allowed",1, HttpStatus.NO_CONTENT);
        }
        AgentGame uplineGame = uplineGameOpt.get();

        Optional<GameProvider> gameDetailOptional = gameProviderRepository.findByProductId(productId);
        if (!gameDetailOptional.isPresent()) {
            throw new ApiException("Error getting game details",1, HttpStatus.FORBIDDEN);
        }
        GameProvider gameDetail = gameDetailOptional.get();

        if (rate > uplineGame.getRate() || rate > gameDetail.getRate()) {
            throw new ApiException("Product error rate limit exceeded",1, HttpStatus.FORBIDDEN);
        }
    }

}
