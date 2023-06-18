package com.tth.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AfterCurrentTimeValidator.class)
public @interface AfterCurrentTime {

	String message() default "must be after current time";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
