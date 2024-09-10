package com.example.backend.security.auth.domain;

import java.util.Optional;
import java.util.UUID;

public interface UserAuthenticationRepository {

    Optional<UserAuthentication> findById(UUID id);
    Optional<UserAuthentication> findByUsername(String username);
    UserAuthentication save(UserAuthentication userAuthentication);
}
