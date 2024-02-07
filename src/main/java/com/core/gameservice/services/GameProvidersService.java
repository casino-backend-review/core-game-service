package com.core.gameservice.services;

import com.core.gameservice.entity.GameProvider;
import com.core.gameservice.exception.ApiException;

import java.util.List;

public interface GameProvidersService {
    public List<GameProvider> getAllProviders() throws ApiException;

    GameProvider createGameProvider(GameProvider gameProvider) throws ApiException;
}
