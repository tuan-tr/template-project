package com.tth.common.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FailureResponseBody {
	private ErrorResponse error;

	public FailureResponseBody(String code, String message, Object details) {
		this.error = new ErrorResponse(code, message, details);
	}

	public FailureResponseBody(String code, String message) {
		this.error = new ErrorResponse(code, message);
	}

	@AllArgsConstructor
	@Getter
	private class ErrorResponse {
		private String code;
		private String message;
		private Object details;

		public ErrorResponse(String code, String message) {
			this.code = code;
			this.message = message;
		}

	}

}
