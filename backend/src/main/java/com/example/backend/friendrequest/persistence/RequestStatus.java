package com.example.backend.friendrequest.persistence;

import lombok.Getter;

@Getter
public enum RequestStatus {
    PENDING(1),
    ACCEPTED(2),
    REJECTED(3);

    private final Integer id;

    RequestStatus(Integer id) {
        this.id = id;
    }
}
