package com.example.backend.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public List<User> search(String searchParam, Pageable pageable) {
        return repository.search(searchParam, pageable);
    }
}
