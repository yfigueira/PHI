package com.example.backend.security.auth.domain;

import com.example.backend.security.auth.exception.UserAuthenticationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class UserAuthenticationService {

    private final UserAuthenticationRepository repository;

    public UserAuthentication findByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> UserAuthenticationException.notFound(UserAuthentication.class, username));
    }

    public UserAuthentication create(@Valid UserAuthentication userAuthentication) {
        return repository.save(userAuthentication);
    }
}
