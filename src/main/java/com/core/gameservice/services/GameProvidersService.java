package com.core.gameservice.services;

import com.core.gameservice.entity.GameProvider;

import java.util.List;

public interface GameProvidersService {
    public List<GameProvider> getAllProviders() ;

    GameProvider createGameProvider(GameProvider gameProvider);
}
