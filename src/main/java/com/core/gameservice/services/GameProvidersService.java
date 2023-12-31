package com.core.gameservice.services;

import com.core.gameservice.entity.GameProvider;

import java.util.List;

public interface GameProvidersService {
    List<String> getAllProviders();

    GameProvider createGameProvider(GameProvider gameProvider);
}
