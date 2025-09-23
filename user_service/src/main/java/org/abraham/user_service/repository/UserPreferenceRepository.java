package org.abraham.user_service.repository;

import org.abraham.user_service.entity.UserPreference;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserPreferenceRepository extends ReactiveCrudRepository<UserPreference, UUID> {

}
