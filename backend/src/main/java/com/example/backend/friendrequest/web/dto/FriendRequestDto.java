package com.example.backend.friendrequest.web.dto;

import com.example.backend.common.web.DtoMapper;
import com.example.backend.friendrequest.domain.FriendRequest;
import com.example.backend.friendrequest.domain.RequestStatus;
import lombok.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Builder
public record FriendRequestDto(
        UUID id,
        UUID sender,
        UUID receiver,
        RequestStatus status
) {
    @Mapper
    public interface FriendRequestDtoMapper extends DtoMapper<FriendRequestDto, FriendRequest> {}

    public static FriendRequestDtoMapper mapper() {
        return Mappers.getMapper(FriendRequestDtoMapper.class);
    }
}
