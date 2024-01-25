package com.core.gameservice.services.impl;

import com.core.gameservice.entity.GameProvider;
import com.core.gameservice.enums.Status;
import com.core.gameservice.repositories.GameProviderRepository;
import com.core.gameservice.services.GameProvidersService;
import lombok.AllArgsConstructor;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<GameProvider> getAllProviders() {
        List<GameProvider> byStatus = gameProviderRepository.findByStatus(Status.A);
        if(!CollectionUtils.isEmpty(byStatus)) {
           return byStatus;
        }
        else{
            throw new ApiException("No game providers found");
        }
    }

    @Override
    public GameProvider createGameProvider(GameProvider gameProvider) {
        gameProvider.setCreatedAt(LocalDateTime.now());
        return gameProviderRepository.save(gameProvider);
    }
}
