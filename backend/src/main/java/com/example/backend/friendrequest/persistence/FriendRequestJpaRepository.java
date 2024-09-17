package com.example.backend.friendrequest.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FriendRequestJpaRepository extends JpaRepository<FriendRequestEntity, UUID> {

}
