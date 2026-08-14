package com.kreitify.api.application.dto;

public class ApiError {
    private final String field;
    private final String message;

    public ApiError(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}