package org.abraham.user_service.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface Address extends ReactiveCrudRepository<Address, UUID> {
}
