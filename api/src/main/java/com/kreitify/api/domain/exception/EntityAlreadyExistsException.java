package com.kreitify.api.domain.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String message) { super(message); }
}
