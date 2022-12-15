package com.tth.common.exception;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BadBusinessRequestException extends RuntimeException {

	private String code;
	private Map<String, ?> details;

}
