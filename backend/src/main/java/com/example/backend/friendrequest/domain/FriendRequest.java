package com.example.backend.friendrequest.domain;

import java.util.UUID;

public record FriendRequest(
        UUID id,
        UUID sender,
        UUID receiver,
        RequestStatus status
) {
}
