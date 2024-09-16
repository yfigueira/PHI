package com.example.backend.security.auth.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

class UserAuthenticationTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();


    @ParameterizedTest
    @MethodSource("validUserAuthentications")
    void testValidUserAuthentication(UserAuthentication userAuthentication) {
        // given
        Set<ConstraintViolation<UserAuthentication>> violations = validator.validate(userAuthentication);

        // then
        assertThat(violations, hasSize(0));
    }

    @ParameterizedTest
    @MethodSource("invalidUserAuthentications")
    void testInvalidUserAuthentication(UserAuthentication userAuthentication) {
        // given
        Set<ConstraintViolation<UserAuthentication>> violations = validator.validate(userAuthentication);

        // then
        assertThat(violations, hasSize(1));
    }

    @Test
    void testUserAuthenticationWithEmptyEmail() {
        // given
        var userAuthentication = UserAuthentication.builder()
                .id(UUID.randomUUID())
                .authId(UUID.randomUUID())
                .firstName("firstName")
                .lastName("lastName")
                .email("") /* empty email */
                .build();

        Set<ConstraintViolation<UserAuthentication>> violations = validator.validate(userAuthentication);

        // then
        assertThat(violations, hasSize(1));
    }

    private static Stream<UserAuthentication> validUserAuthentications() {
        return Stream.of(
            UserAuthentication.builder()
                    .id(UUID.randomUUID())
                    .authId(UUID.randomUUID())
                    .firstName("Valid")
                    .lastName("Valid")
                    .email("valid@email.com")
                    .build(),

            UserAuthentication.builder()
                    .authId(UUID.randomUUID())
                    .firstName("Valid")
                    .lastName("Valid")
                    .email("valid@email.com")
                    .build(),

            UserAuthentication.builder()
                    .id(UUID.randomUUID())
                    .firstName("Valid")
                    .lastName("Valid")
                    .email("valid@email.com")
                    .build()
        );
    }

    private static Stream<UserAuthentication> invalidUserAuthentications() {
        return Stream.of(
                UserAuthentication.builder()
                        .id(UUID.randomUUID())
                        .authId(UUID.randomUUID())
                        .firstName("") /* missing first name */
                        .lastName("lastName")
                        .email("test@email.com")
                        .build(),

                UserAuthentication.builder()
                        .id(UUID.randomUUID())
                        .authId(UUID.randomUUID())
                        .firstName("firstName")
                        .lastName("") /* missing last name */
                        .email("test@email.com")
                        .build(),

                UserAuthentication.builder()
                        .id(UUID.randomUUID())
                        .authId(UUID.randomUUID())
                        .firstName("firstName")
                        .lastName("lastName")
                        .email("invalidEmail.com") /* wrong email format */
                        .build(),

                UserAuthentication.builder()
                        .id(UUID.randomUUID())
                        .authId(UUID.randomUUID())
                        .firstName("firstName")
                        .lastName("lastName")
                        .email("invalid@Email") /* wrong email format */
                        .build(),

                UserAuthentication.builder()
                        .id(UUID.randomUUID())
                        .authId(UUID.randomUUID())
                        .firstName("firstName")
                        .lastName("lastName")
                        .email("invalidEmail") /* wrong email format */
                        .build()
        );
    }
}