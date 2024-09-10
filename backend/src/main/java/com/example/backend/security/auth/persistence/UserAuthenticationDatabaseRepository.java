package com.example.backend.security.auth.persistence;

import com.example.backend.security.auth.domain.UserAuthentication;
import com.example.backend.security.auth.domain.UserAuthenticationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
class UserAuthenticationDatabaseRepository implements UserAuthenticationRepository {

    private final UserAuthenticationJpaRepository jpaRepository;
    private final UserAuthenticationMapper mapper;

    @Override
    public Optional<UserAuthentication> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<UserAuthentication> findByUsername(String username) {
        return jpaRepository.findByEmail(username)
                .map(mapper::toDomain);
    }

    @Override
    public UserAuthentication save(UserAuthentication userAuthentication) {
        var entity = mapper.toEntity(userAuthentication);
        return mapper.toDomain(jpaRepository.save(entity));
    }
}
