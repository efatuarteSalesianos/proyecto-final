package com.salesianostriana.dam.finalapi.validation.simple.validators;

import com.salesianostriana.dam.finalapi.validation.simple.anotations.FutureDate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class FutureDateValidator implements ConstraintValidator<FutureDate, LocalDateTime> {

    private LocalDateTime date;

    @Override
    public void initialize(FutureDate constraintAnnotation) { }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        return date.isAfter(LocalDateTime.now());
    }
}
