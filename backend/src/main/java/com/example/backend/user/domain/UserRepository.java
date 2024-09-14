package com.example.backend.user.domain;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRepository {

    List<User> search(String searchParam, Pageable pageable);
}
