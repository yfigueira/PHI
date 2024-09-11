package com.example.backend.user.persistence;

import com.example.backend.common.persistence.EntityMapper;
import com.example.backend.user.domain.User;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.*;

@Mapper(componentModel = SPRING)
public interface UserMapper extends EntityMapper<UserEntity, User> {

}
