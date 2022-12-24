package com.tth.common.jackson;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Component
@AllArgsConstructor
@Log4j2
public class JsonParserProvider {
	
	private ObjectMapper objectMapper;
	
	public String toString(Object object) {
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException ex) {
			log.error(ExceptionUtils.getStackTrace(ex));
			return object.toString();
		}
	}

}
