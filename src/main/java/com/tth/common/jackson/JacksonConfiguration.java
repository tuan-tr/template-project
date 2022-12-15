package com.tth.common.jackson;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class JacksonConfiguration {

	@Bean
	ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new Jackson2ObjectMapperBuilder()
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
						DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE,
						SerializationFeature.FAIL_ON_EMPTY_BEANS)
				// .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
				.serializationInclusion(JsonInclude.Include.NON_NULL)
				.mixIn(PageImpl.class, PageImplMixIn.class)
				.mixIn(FieldError.class, BindExceptionMixIn.class)
				.createXmlMapper(false)
				.build();

		return objectMapper;
	}

	abstract class PageImplMixIn {
		@JsonProperty("page") abstract Object getNumber();
		@JsonIgnore abstract Object getNumberOfElements();
		@JsonIgnore abstract Object getPageable();
		@JsonIgnore abstract Object getSort();
		@JsonIgnore abstract Object isEmpty();
	}

	abstract class BindExceptionMixIn {
		@JsonIgnore abstract Object getArguments();
		@JsonIgnore abstract Object getCodes();
	}

}
