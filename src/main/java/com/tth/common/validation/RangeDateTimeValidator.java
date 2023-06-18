package com.tth.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;

public class RangeDateTimeValidator implements ConstraintValidator<ValidRangeDateTime, Object> {

	private String fromFieldName;
	private String toFieldName;

	@Override
	public void initialize(ValidRangeDateTime constraintAnnotation) {
		fromFieldName = constraintAnnotation.from();
		toFieldName = constraintAnnotation.to();
	}

	// TODO
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		try {
			Field fromField = value.getClass().getDeclaredField(fromFieldName);
			Field toField = value.getClass().getDeclaredField(toFieldName);
			fromField.setAccessible(true);
			toField.setAccessible(true);
			OffsetDateTime fromTime = (OffsetDateTime) fromField.get(value);
			OffsetDateTime toTime = (OffsetDateTime) toField.get(value);
			
			if (fromTime == null || toTime == null || fromTime.isBefore(toTime)) {
				return true;
			}
			
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("must be before " + toFieldName)
					.addPropertyNode(fromFieldName)
					.addConstraintViolation();
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}

}
