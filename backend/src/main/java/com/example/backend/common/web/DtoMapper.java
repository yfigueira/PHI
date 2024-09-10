package com.example.backend.common.web;

public interface DtoMapper<DTO, DOMAIN> {
    DOMAIN toDomain(DTO dto);
    DTO toDto(DOMAIN domain);
}
