package com.kreitify.api.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RestrictedBy {
    Class<?> dependentEntity();
    String fieldName();
    String displayName() default "registros asociados";
}
