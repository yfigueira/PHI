package com.example.backend.friendrequest.domain;

import lombok.With;

import java.util.UUID;

public record FriendRequest(
        UUID id,
        UUID sender,
        UUID receiver,
        @With RequestStatus status
) {
}
