package com.example.backend.security.auth.web.dto;

import com.example.backend.common.web.DtoMapper;
import com.example.backend.security.auth.domain.UserAuthentication;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

public record UserAuthenticationDto(
        UUID authId,
        String firstName,
        String lastName,
        String email
) {
    @Mapper
    public interface UserAuthenticationDtoMapper extends DtoMapper<UserAuthenticationDto, UserAuthentication>{}

    public static UserAuthenticationDtoMapper mapper() {
        return Mappers.getMapper(UserAuthenticationDtoMapper.class);
    }
}
