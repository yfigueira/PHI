package com.example.backend.common.exception;

import lombok.Builder;

@Builder
public record ValidationErrorResponse(
        String message,
        Object rejectedValue
) {
}
