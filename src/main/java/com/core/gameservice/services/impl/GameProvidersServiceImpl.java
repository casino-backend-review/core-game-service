package com.core.gameservice.services.impl;

import com.core.gameservice.entity.GameProvider;
import com.core.gameservice.enums.Status;
import com.core.gameservice.exception.ApiException;
import com.core.gameservice.repositories.GameProviderRepository;
import com.core.gameservice.services.GameProvidersService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameProvidersServiceImpl implements GameProvidersService {

    GameProviderRepository gameProviderRepository;
    @Override
    public List<GameProvider> getAllProviders() throws ApiException {
        List<GameProvider> byStatus = gameProviderRepository.findByStatus(Status.A);
        if(!CollectionUtils.isEmpty(byStatus)) {
           return byStatus;
        }
        else{
            throw new ApiException("No game providers found",1, HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public GameProvider createGameProvider(GameProvider gameProvider) throws ApiException {
        gameProvider.setCreatedAt(LocalDateTime.now());
        try {
           return gameProviderRepository.save(gameProvider);
        }
        catch (Exception exception){
            throw  new ApiException("Failed to create game provider",1,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
