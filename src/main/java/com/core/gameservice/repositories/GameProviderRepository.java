package com.core.gameservice.repositories;

import com.core.gameservice.entity.GameProvider;
import com.core.gameservice.enums.Status;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameProviderRepository extends MongoRepository<GameProvider, Long> {
    Optional<GameProvider> findByProductId(String productId);

   List<GameProvider> findByStatus(Status status);
}
