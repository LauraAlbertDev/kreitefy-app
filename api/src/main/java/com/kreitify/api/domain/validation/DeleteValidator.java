package com.kreitify.api.domain.validation;

public interface DeleteValidator<T> {
    void validate(T entity);
}
