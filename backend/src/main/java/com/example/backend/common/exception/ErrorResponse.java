package com.example.backend.common.exception;

import lombok.Builder;

@Builder
public record ErrorResponse(
        String responseStatus,
        int statusCode,
        String message
) {
}
