package com.example.backend.common.persistence;

public interface EntityMapper<ENTITY, DOMAIN> {

    ENTITY toEntity(DOMAIN domain);
    DOMAIN toDomain(ENTITY entity);
}
