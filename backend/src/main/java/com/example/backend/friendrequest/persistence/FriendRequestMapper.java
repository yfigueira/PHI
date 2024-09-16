package com.example.backend.friendrequest.persistence;

import com.example.backend.common.persistence.EntityMapper;
import com.example.backend.friendrequest.domain.FriendRequest;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.*;

@Mapper(componentModel = SPRING)
public interface FriendRequestMapper extends EntityMapper<FriendRequestEntity, FriendRequest> {
}
