package com.example.backend.common.exception;

import java.util.UUID;

public abstract class ResourceException extends RuntimeException {
    public ResourceException() { }

    public ResourceException(String message) { super(message); }

    public static NotFoundException notFound(Class<?> clazz, UUID id) {
        return new NotFoundException("%s with id [ %s ] not found".formatted(clazz.getSimpleName(), id));
    }

    public static class NotFoundException extends RuntimeException { public NotFoundException(String message) { super(message); } }
}
