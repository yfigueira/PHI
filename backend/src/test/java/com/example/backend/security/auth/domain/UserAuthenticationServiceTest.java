package com.example.backend.security.auth.domain;

import com.example.backend.security.auth.exception.UserAuthenticationException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserAuthenticationServiceTest {

    @InjectMocks
    private UserAuthenticationService service;

    @Mock
    private UserAuthenticationRepository repository;


    @Test
    void findByUsername_whenUserExists_shouldReturnUserAuthentication() {
        // given
        var validUsername = "username@email.com";
        var mockUserAuthentication = UserAuthentication.builder()
                .id(UUID.randomUUID())
                .authId(UUID.randomUUID())
                .firstName("User")
                .lastName("Name")
                .email(validUsername)
                .build();

        doReturn(Optional.of(mockUserAuthentication)).when(repository).findByUsername(validUsername);

        // when
        var result = service.findByUsername(validUsername);

        // then
        verify(repository, times(1)).findByUsername(validUsername);
        assertAll(
                () -> assertThat(result, notNullValue()),
                () -> assertThat(result, Matchers.isA(UserAuthentication.class)),
                () -> assertThat(result, is(equalTo(mockUserAuthentication)))
        );
    }

    @Test
    void findByUsername_whenUserDoesNotExist_shouldThrowUserAuthenticationNotFoundException() {
        // given
        var unknownUsername = "unknown";

        // then
        assertAll(
            () -> assertThatThrownBy(() -> service.findByUsername(unknownUsername))
                    .isInstanceOf(UserAuthenticationException.NotFoundException.class)
                    .hasMessage("%s with username [ %s ] not found".formatted(UserAuthentication.class.getSimpleName(), unknownUsername))
                    .hasNoCause(),

            () -> verify(repository, times(1)).findByUsername(unknownUsername)
        );
    }

    @Test
    void create_whenInputIsValid_shouldReturnCreatedUserAsUserAuthentication() {
        // given
        UUID id = UUID.randomUUID();
        UUID authId = UUID.randomUUID();
        String firstName = "New";
        String lastName = "User";
        String email = "newuser@email.com";

        var received = UserAuthentication.builder()
                .authId(authId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();

        var created = UserAuthentication.builder()
                .id(id)
                .authId(authId)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();

        doReturn(created).when(repository).save(received);

        // when
        var result = service.create(received);

        // then
        assertAll(
                () -> assertThat(result, notNullValue()),
                () -> assertThat(result, Matchers.isA(UserAuthentication.class)),
                () -> assertThat(result, is(equalTo(created)))
        );
    }
}