package com.salesianostriana.dam.finalapi.validation.simple.anotations;

import com.salesianostriana.dam.finalapi.validation.simple.validators.UniqueUsernameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername {
    String message() default "Username already exists, please choose another one";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}