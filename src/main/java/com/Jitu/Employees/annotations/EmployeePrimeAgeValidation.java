package com.Jitu.Employees.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = {EmployeePrimeAgeValidator.class})
public @interface EmployeePrimeAgeValidation {
    String message() default "Pls enter a prime number for age.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
