package com.tth.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;

@Log4j2
public class RangeDateTimeValidator implements ConstraintValidator<ValidRangeDateTime, Object> {

	private String fromFieldName;
	private String toFieldName;

	@Override
	public void initialize(ValidRangeDateTime constraintAnnotation) {
		fromFieldName = constraintAnnotation.from();
		toFieldName = constraintAnnotation.to();
	}

	// TODO handle for another datetime type
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
		} catch (Exception ex) {
			log.warn(ExceptionUtils.getStackTrace(ex));
			return true;
		}
		return false;
	}

}
