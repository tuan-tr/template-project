package com.tth.common.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public class FailureResponseBody {
	private ErrorResponse error;

	public FailureResponseBody(String code, String message) {
		this.error = new ErrorResponse(code, message);
	}

	public FailureResponseBody(String code, String message, Object details) {
		this.error = new ErrorResponse(code, message, details);
	}

	public FailureResponseBody(String code, String message, List<FieldError> fieldErrors) {
		this.error = new ErrorResponse(code, message, fieldErrors);
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
		
		public ErrorResponse(String code, String message, List<FieldError> fieldErrors) {
			List<FieldErrorResponse> details = fieldErrors.stream()
					.map(e -> new FieldErrorResponse(e))
					.collect(Collectors.toList());
			
			this.code = code;
			this.message = message;
			this.details = details;
		}
	}

	@Getter
	private class FieldErrorResponse {
		private String defaultMessage;
		private String objectName;
		private String field;
		private String code;

		public FieldErrorResponse(FieldError fieldError) {
			this.defaultMessage = fieldError.getDefaultMessage();
			this.objectName = fieldError.getObjectName();
			this.field = fieldError.getField();
			this.code = fieldError.getCode();
		}
	}

}
