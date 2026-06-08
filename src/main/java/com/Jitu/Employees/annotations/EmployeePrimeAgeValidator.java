package com.Jitu.Employees.annotations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeePrimeAgeValidator implements ConstraintValidator<EmployeePrimeAgeValidation,Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        for (int i = 3; i <= Math.sqrt(value); i++) {
            if(value%i == 0) return false;
        }
        return  true;
    }
}
