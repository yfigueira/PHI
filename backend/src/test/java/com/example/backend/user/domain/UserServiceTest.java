package com.example.backend.user.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    @Test
    void search_whenResultsForSearchParamExist_shouldReturnListOfMatchingResults() {
        // given
        var searchParam = "test";
        var pageRequest = PageRequest.of(0, 5);
        var matchingUsers = List.of(
                User.builder().id(UUID.randomUUID()).firstName("Test").lastName("User").build(),
                User.builder().id(UUID.randomUUID()).firstName("User").lastName("Test").build(),
                User.builder().id(UUID.randomUUID()).firstName("testuser").lastName("test").build()
        );

        doReturn(matchingUsers).when(repository).search(searchParam, pageRequest);

        // when
        var result = service.search(searchParam, pageRequest);

        // then
        assertAll(
                () -> assertThat(result, not(emptyCollectionOf(User.class))),
                () -> assertThat(result, hasSize(3))
        );
    }

    @Test
    void search_whenNoResultsForSearchParam_shouldReturnEmptyCollection() {
        // given
        var unmatchedParam = "unmatched";
        var pageRequest = PageRequest.of(0, 5);

        doReturn(List.of()).when(repository).search(unmatchedParam, pageRequest);

        // when
        var result = service.search(unmatchedParam, pageRequest);

        // then
        assertThat(result, is(emptyCollectionOf(User.class)));
    }
}