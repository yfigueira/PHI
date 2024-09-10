package com.example.backend.security.auth.persistence;

import com.example.backend.common.persistence.EntityMapper;
import com.example.backend.security.auth.domain.UserAuthentication;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.*;

@Mapper(componentModel = SPRING)
public interface UserAuthenticationMapper extends EntityMapper<UserAuthenticationEntity, UserAuthentication> {

}
