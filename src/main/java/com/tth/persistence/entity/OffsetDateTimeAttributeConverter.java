package com.tth.persistence.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.OffsetDateTime;
import java.time.ZoneId;

@Converter(autoApply = true)
public class OffsetDateTimeAttributeConverter implements AttributeConverter<OffsetDateTime, OffsetDateTime> {

	@Override
	public OffsetDateTime convertToDatabaseColumn(OffsetDateTime attribute) {
		return attribute;
	}

	@Override
	public OffsetDateTime convertToEntityAttribute(OffsetDateTime dbData) {
		return dbData.atZoneSameInstant(ZoneId.systemDefault()).toOffsetDateTime();
	}

}
