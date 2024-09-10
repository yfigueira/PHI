package com.example.backend.security.auth.exception;

import com.example.backend.common.exception.ResourceException;


public class UserAuthenticationException extends ResourceException {

    public UserAuthenticationException(String message) {
        super(message);
    }

    public static NotFoundException notFound(Class<?> clazz, String username) {
        return new NotFoundException("%s with username [ %s ] not found".formatted(clazz.getSimpleName(), username));
    }
}
