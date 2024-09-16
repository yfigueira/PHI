package com.example.backend.friendrequest.persistence;

import com.example.backend.friendrequest.domain.RequestStatus;
import jakarta.persistence.AttributeConverter;

import java.util.stream.Stream;

public class RequestStatusConverter implements AttributeConverter<RequestStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(RequestStatus attribute) {
        return attribute != null ? attribute.getId() : null;
    }

    @Override
    public RequestStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) return null;

        return Stream.of(RequestStatus.values())
                .filter(rs -> rs.getId().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
