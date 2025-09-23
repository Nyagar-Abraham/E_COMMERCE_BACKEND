package org.abraham.user_service.repository;


import org.abraham.user_service.entity.UserProfile;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserProfileRepository extends ReactiveCrudRepository<UserProfile, UUID> {
    @Query("SELECT * FROM user_profiles WHERE user_id = :userId ")
    Mono<UserProfile> findByUserId(UUID userId);
}
