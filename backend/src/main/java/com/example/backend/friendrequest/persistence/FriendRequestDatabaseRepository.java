package com.example.backend.friendrequest.persistence;

import com.example.backend.friendrequest.domain.FriendRequest;
import com.example.backend.friendrequest.domain.FriendRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FriendRequestDatabaseRepository implements FriendRequestRepository {

    private final FriendRequestJpaRepository jpaRepository;

    private final FriendRequestMapper mapper;

    @Override
    public FriendRequest save(FriendRequest request) {
        var entity = mapper.toEntity(request);
        return mapper.toDomain(jpaRepository.save(entity));
    }
}
