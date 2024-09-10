package com.example.backend.security.auth.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.UUID;

@Builder
public record UserAuthentication(
        UUID id,
        UUID authId,
        @NotBlank(message = "First name required")
        String firstName,
        @NotBlank(message = "Last name required")
        String lastName,
        @Email(message = "Wrong email format",
                regexp = "^[a-zA-Z0-9.-_%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,6}$")
        String email
) {
}
