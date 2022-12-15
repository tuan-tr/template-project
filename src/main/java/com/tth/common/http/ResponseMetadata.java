package com.tth.common.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseMetadata {

	private String code;
	private String message;
	private Object details;

	public ResponseMetadata(String code, String message) {
		this.code = code;
		this.message = message;
	}

}
