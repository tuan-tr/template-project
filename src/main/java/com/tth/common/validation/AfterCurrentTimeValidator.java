package com.tth.common.validation;

import java.time.OffsetDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AfterCurrentTimeValidator implements ConstraintValidator<AfterCurrentTime, Object> {

	// TODO
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value instanceof OffsetDateTime) {
			OffsetDateTime timeValue = (OffsetDateTime) value;
			if (timeValue.isAfter(OffsetDateTime.now())) {
				return true;
			}
		} else {
			return true;
		}
		
		return false;
	}

}
