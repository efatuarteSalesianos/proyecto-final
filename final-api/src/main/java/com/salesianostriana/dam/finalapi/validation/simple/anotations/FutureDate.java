package com.salesianostriana.dam.finalapi.validation.simple.anotations;

import com.salesianostriana.dam.finalapi.validation.simple.validators.FutureDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureDateValidator.class)
@Documented
public @interface FutureDate {
    String message() default "La fecha introducida deber ser una fecha posterior a la actual.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
