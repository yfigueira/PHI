package com.example.backend.user.persistence;

import com.example.backend.user.domain.User;
import com.example.backend.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
class UserDatabaseRepository implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    @Override
    public List<User> search(String searchParam, Pageable pageable) {
        var entities = jpaRepository.search(searchParam, pageable).getContent();
        return entities.stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
