package com.example.backend.friendrequest.persistence;

import com.example.backend.friendrequest.domain.RequestStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
class RequestStatusConverterTest {

    @InjectMocks
    private RequestStatusConverter converter;

    @Test
    void shouldConvertToRequestStatus() {
        // given
        var dbPendingStatus = 1;
        var dbAcceptedStatus = 2;
        var dbRejectedStatus = 3;

        // when
        var pendingResult = converter.convertToEntityAttribute(dbPendingStatus);
        var acceptedResult = converter.convertToEntityAttribute(dbAcceptedStatus);
        var rejectedResult = converter.convertToEntityAttribute(dbRejectedStatus);

        // then
        assertThat(pendingResult, is(equalTo(RequestStatus.PENDING)));
        assertThat(acceptedResult, is(equalTo(RequestStatus.ACCEPTED)));
        assertThat(rejectedResult, is(equalTo(RequestStatus.REJECTED)));
    }

    @Test
    void shouldConvertToDatabaseStatusIdColumn() {
        // given
        var domainPendingStatus = RequestStatus.PENDING;
        var domainAcceptedStatus = RequestStatus.ACCEPTED;
        var domainRejectedStatus = RequestStatus.REJECTED;

        // when
        var pendingResult = converter.convertToDatabaseColumn(domainPendingStatus);
        var acceptedResult = converter.convertToDatabaseColumn(domainAcceptedStatus);
        var rejectedResult = converter.convertToDatabaseColumn(domainRejectedStatus);

        // then
        assertThat(pendingResult, is(equalTo(1)));
        assertThat(acceptedResult, is(equalTo(2)));
        assertThat(rejectedResult, is(equalTo(3)));
    }

    @Test
    void whenInvalidStatusValue_shouldThrowIllegalArgumentException() {
        // given
        var invalidStatus = -1;

        // when, then
        assertThatThrownBy(() -> converter.convertToEntityAttribute(invalidStatus))
                .isInstanceOf(IllegalArgumentException.class)
                .hasNoCause();
    }
}