package com.salesianostriana.dam.finalapi.validation.simple.validators;

import com.salesianostriana.dam.finalapi.validation.simple.anotations.FutureDate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;

public class FutureDateValidator implements ConstraintValidator<FutureDate, LocalDateTime> {

    private LocalDateTime date;

    @Override
    public void initialize(FutureDate constraintAnnotation) { }

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        return date.isBefore(LocalDateTime.now());
    }
}
