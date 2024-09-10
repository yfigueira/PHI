package com.example.backend.common.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Set;


@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceException.NotFoundException.class)
    public ResponseEntity<?> handleNotFound(ResourceException.NotFoundException exception) {
        return handle(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException exception) {
        return handle(HttpStatus.BAD_REQUEST, exception.getConstraintViolations());
    }

    private ResponseEntity<ErrorResponse> handle(HttpStatus status, RuntimeException exception) {
        var errorResponse = ErrorResponse.builder()
                .responseStatus(status.name())
                .statusCode(status.value())
                .message(exception.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }

    private ResponseEntity<List<ValidationErrorResponse>> handle(HttpStatus status, Set<ConstraintViolation<?>> violations) {
        var body = violations.stream()
                .map(cv -> ValidationErrorResponse.builder()
                        .message(cv.getMessage())
                        .rejectedValue(cv.getInvalidValue())
                        .build())
                .toList();

        return new ResponseEntity<>(body, status);
    }
}
