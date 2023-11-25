package com.core.gameservice.repositories;

import com.core.gameservice.entity.GameProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameProviderRepository extends JpaRepository<GameProvider, Long> {
    Optional<GameProvider> findByProductId(String productId);
}
