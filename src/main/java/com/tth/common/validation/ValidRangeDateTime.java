package com.tth.common.validation;

import com.tth.common.validation.ValidRangeDateTime.ValidRangeDateTimes;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(ValidRangeDateTimes.class)
@Constraint(validatedBy = RangeDateTimeValidator.class)
public @interface ValidRangeDateTime {

	String message() default "{com.tth.common.validation.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String from();

	String to();

	@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ValidRangeDateTimes {
		ValidRangeDateTime[] value();
	}

}
