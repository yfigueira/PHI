package com.example.backend.security.auth.web.dto;

import com.example.backend.common.web.DtoMapper;
import com.example.backend.security.auth.domain.UserAuthentication;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

public record UserAuthenticationSummary(
        UUID id,
        String firstName,
        String lastName,
        String email
) {
    @Mapper
    public interface UserAuthenticationSummaryMapper extends DtoMapper<UserAuthenticationSummary, UserAuthentication>{}

    public static UserAuthenticationSummaryMapper mapper() {
        return Mappers.getMapper(UserAuthenticationSummaryMapper.class);
    }
}
