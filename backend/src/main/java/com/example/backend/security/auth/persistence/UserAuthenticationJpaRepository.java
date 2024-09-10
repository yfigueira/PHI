package com.example.backend.security.auth.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserAuthenticationJpaRepository extends JpaRepository<UserAuthenticationEntity, UUID> {

    Optional<UserAuthenticationEntity> findByEmail(String email);
}
