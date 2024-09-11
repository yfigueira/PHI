package com.example.backend.user.web.dto;

import com.example.backend.common.web.DtoMapper;
import com.example.backend.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

public record UserSummary(
        UUID id,
        String firstName,
        String lastName,
        String email
) {
    @Mapper
    public interface UserSummaryMapper extends DtoMapper<UserSummary, User>{}

    public static UserSummaryMapper mapper() { return Mappers.getMapper(UserSummaryMapper.class); }
}
