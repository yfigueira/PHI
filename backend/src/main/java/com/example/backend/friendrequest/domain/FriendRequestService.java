package com.example.backend.friendrequest.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository repository;

    public FriendRequest create(FriendRequest request) {
        return repository.save(request.withStatus(RequestStatus.PENDING));
    }
}
