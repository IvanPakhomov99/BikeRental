package com.gl.tasks.bike.rental.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnsureNumberValidator implements ConstraintValidator<EnsureNumber, Object> {
    private EnsureNumber ensureNumber;

    @Override
    public void initialize(EnsureNumber constraintAnnotation) {
        this.ensureNumber = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        String regex = ensureNumber.decimal() ? "-?[0-9][0-9\\.\\,]*" : "-?[0-9]+";
        String data = String.valueOf(value);
        return data.matches(regex);
    }

}