package com.core.gameservice.repositories;

import com.core.gameservice.entity.MemberBetLimit;
import com.core.gameservice.enums.Status;
import com.core.gameservice.enums.UserType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberBetLimitRepository extends MongoRepository<MemberBetLimit,String> {

    List<MemberBetLimit> findByUsernameAndUserType(String username, UserType userType);

    List<MemberBetLimit> findByUsernameAndStatus(String username, Status status);

    void deleteAllByUsername(String username);
}
