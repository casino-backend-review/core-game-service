package com.core.gameservice.services.impl;

import com.core.gameservice.client.MemberClient;
import com.core.gameservice.dto.*;
import com.core.gameservice.entity.AgentGame;
import com.core.gameservice.entity.GameProvider;
import com.core.gameservice.enums.Status;
import com.core.gameservice.enums.UserType;
import com.core.gameservice.exception.ApiException;
import com.core.gameservice.exception.ApiResponseMessage;
import com.core.gameservice.repositories.AgentGameRepository;
import com.core.gameservice.repositories.GameProviderRepository;
import com.core.gameservice.services.AgentGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AgentGameServiceImpl implements AgentGameService {

    private final AgentGameRepository agentGameRepository;
    private final GameProviderRepository gameProviderRepository;
    private static final List<UserType> memberCreationPermissionUserType = Collections.unmodifiableList(List.of(UserType.agent, UserType.master, UserType.senior, UserType.super_senior));
    private final MemberClient memberClient;

    @Autowired
    public AgentGameServiceImpl(AgentGameRepository agentGameRepository,
                                GameProviderRepository gameProviderRepository, MemberClient memberClient) {
        this.agentGameRepository = agentGameRepository;
        this.gameProviderRepository = gameProviderRepository;
        this.memberClient = memberClient;
    }

    @Override
    public List<AgentGameResponse> createAgentGame(CreateAgentGameRequest request) throws ApiException {
        long gameUplineCount = agentGameRepository.countByUsername(request.getUpline());
        if (!request.getUserType().equals(UserType.company) && gameUplineCount == 0) {
            throw new ApiException("Upline not found",1, HttpStatus.NO_CONTENT);
        }

        for (Product game : request.getProducts()) {
            if (game.isChecked()) {
                Optional<GameProvider> gameDetailOptional = gameProviderRepository.findByProductId(game.getProductId());
                if (gameDetailOptional.isEmpty()) {
                    throw new ApiException("Product not found",1, HttpStatus.NO_CONTENT);
                }
                GameProvider gameDetail = gameDetailOptional.get();

                if (!request.getUserType().equals(UserType.company) && !request.getUserType().equals(UserType.admin)) {
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

    private void processProduct(String username, UserType userType, String upline, Product game) throws ApiException {
        if (game.isChecked()) {
            Optional<GameProvider> gameDetailOptional = gameProviderRepository.findByProductId(game.getProductId());
            if (gameDetailOptional.isEmpty()) {
                throw new ApiException("Product not found",1, HttpStatus.NO_CONTENT);
            }
            GameProvider gameDetail = gameDetailOptional.get();

            if (!userType.equals(UserType.company) && !userType.equals(UserType.admin)) {
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
    public HashMap<String, List<AgentGameResponse>> updateAgentGameList(List<UpdateAgentGameByProductRequest> request, String token) throws ApiException {
        HashMap<String, List<AgentGameResponse>> result = new HashMap<>();
        if(!CollectionUtils.isEmpty(request)){

            for (UpdateAgentGameByProductRequest updateAgentGameByProductRequest : request) {
                if (updateAgentGameByProductRequest.getIsDownlineImpact()) {
                    List<AgentGameResponse> agentGameResponses = new ArrayList<>();

                    ApiResponseMessage<List<UserAndDownlineHierarchyInfo>> downline = memberClient.getDownline(updateAgentGameByProductRequest.getUsername(), updateAgentGameByProductRequest.getUserType(), token);

                    List<UserAndDownlineHierarchyInfo> downlineData = downline.getData();

                    Optional<UserAndDownlineHierarchyInfo> userAndDownlineHierarchyInfo = downlineData.stream().filter(data -> data.getUser().getUsername().equals(updateAgentGameByProductRequest.getUsername())).findFirst();

                    if (userAndDownlineHierarchyInfo.isEmpty()) {
                        throw new ApiException("User not found", 1, HttpStatus.NO_CONTENT);
                    }
                    List<AgentGame> updatedGames = new ArrayList<>();
                    updatePercentageAndStatus(updateAgentGameByProductRequest, userAndDownlineHierarchyInfo.get(), downlineData, updatedGames);                            // You can perform further operations with 'info' here


                    processProduct(updateAgentGameByProductRequest.getUsername(), updateAgentGameByProductRequest.getUserType(), updateAgentGameByProductRequest.getUpline(), updateAgentGameByProductRequest.getProduct());

                    updatedGames.forEach(agentGame -> agentGameResponses.add(convertToAgentGameResponse(agentGame)));
                    result.put(updateAgentGameByProductRequest.getUsername(), agentGameResponses);
                }
            }
        }
        return result;
    }

    private void updatePercentageAndStatus(UpdateAgentGameByProductRequest updateAgentGameByProductRequest, UserAndDownlineHierarchyInfo userAndDownlineHierarchyInfo, List<UserAndDownlineHierarchyInfo> downlineData, List<AgentGame> updatedGames) throws ApiException {
        User user = userAndDownlineHierarchyInfo.getUser();
        Product product = updateAgentGameByProductRequest.getProduct();
        if (!user.getType().equals(UserType.company) && !user.getType().equals(UserType.admin)) {
            if (user.getUpline() != null && product.getProductId() != null && product.getRate() != null) {
                validateUplineRate(user.getUpline(), product.getProductId(), product.getRate());
            }
        }
        AgentGame agentGame = agentGameRepository.findByUsernameAndProductId(user.getUsername(), product.getProductId())
                .orElseThrow(() -> new ApiException(String.format("Agent game username %s with the product id %s not found", user.getUsername(), product.getProductId()), 1, HttpStatus.NO_CONTENT));
        ;

        agentGame.setRate(product.getRate());
        agentGame.setRateLimit(product.getRateLimit());
        agentGame.setStatus(product.getNewGameStatus());
        agentGame.setUpdatedAt(LocalDateTime.now());
        agentGameRepository.save(agentGame); //update agent Game
        updatedGames.add(agentGame);
        List<User> downlines = userAndDownlineHierarchyInfo.getDownlines();
        for (User userData : downlines) {
            if (!userData.getType().equals(UserType.member)) {
                Optional<UserAndDownlineHierarchyInfo> first = downlineData.stream().filter(userAndDownlineHierarchyInfo1 -> userAndDownlineHierarchyInfo1.getUser().getUsername().equals(userData.getUsername())).findFirst();

                UserAndDownlineHierarchyInfo newUserAndDownlineHierarchyInfo = UserAndDownlineHierarchyInfo.builder().user(userData).downlines(first.map(UserAndDownlineHierarchyInfo::getDownlines).orElse(null)).build();
                AgentGame agentGame1 = agentGameRepository.findByUsernameAndProductId(userData.getUsername(), product.getProductId()).orElseThrow(() -> new ApiException(String.format("Agent game username %s with the product id %s not found", user.getUsername(), product.getProductId()), 1, HttpStatus.NO_CONTENT));

                Double newRateLimit = product.getRateLimit();
                double oldRate = agentGame1.getRate();
                Double oldRateLimit = agentGame1.getRateLimit();
                Double rate;
                Double rateLimit;
                if (newRateLimit > (oldRate + oldRateLimit)) {
                    Double difference = newRateLimit - (oldRate + oldRateLimit);
                    rate = oldRate + (difference) / 2;
                    rateLimit = oldRateLimit + (difference) / 2;
                } else if (newRateLimit < (oldRate + oldRateLimit)) {
                    Double difference = (oldRate + oldRateLimit) - newRateLimit;
                    rate = oldRate - (difference) / 2;
                    rateLimit = oldRateLimit - (difference) / 2;
                } else {
                    rate = oldRate;
                    rateLimit = oldRateLimit;
                }

                UpdateAgentGameByProductRequest updateAgentGameByProductRequest1 = UpdateAgentGameByProductRequest.builder().
                        username(userData.getUsername())
                        .userType(userData.getType())
                        .upline(userData.getUpline())
                        .product(Product.builder().productId(updateAgentGameByProductRequest.getProduct().getProductId())
                                .productName(updateAgentGameByProductRequest.getProduct().getProductName())
                                .newGameStatus(updateAgentGameByProductRequest.getProduct().getNewGameStatus()).rate(rate).rateLimit(rateLimit).build())
                        .build();

                updatePercentageAndStatus(updateAgentGameByProductRequest1, newUserAndDownlineHierarchyInfo, downlineData, updatedGames);
            }

        }
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

    @Override
    public void deleteAgentGames(List<String> ids) throws ApiException {
      try{
        agentGameRepository.deleteAllById(ids);
    } catch (Exception e) {
        throw new ApiException("Error occurred while deleting all specified agent games: " + e.getMessage(),1, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    }

    private void validateUplineRate(String upline, String productId, double rate) throws ApiException {
        Optional<AgentGame> uplineGameOpt = agentGameRepository.findByUsernameAndProductId(upline, productId);
        if (uplineGameOpt.isEmpty()) {
            throw new ApiException("Error getting game upline or product upline not allowed",1, HttpStatus.NO_CONTENT);
        }
        AgentGame uplineGame = uplineGameOpt.get();

        Optional<GameProvider> gameDetailOptional = gameProviderRepository.findByProductId(productId);
        if (gameDetailOptional.isEmpty()) {
            throw new ApiException("Error getting game details",1, HttpStatus.FORBIDDEN);
        }
        GameProvider gameDetail = gameDetailOptional.get();

        if (rate > uplineGame.getRate() || rate > gameDetail.getRate()) {
            throw new ApiException("Product error rate limit exceeded",1, HttpStatus.FORBIDDEN);
        }
    }

}
