package com.tth.common.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tth.common.http.ResponseBody;
import com.tth.common.http.ResponseMetadata;
import com.tth.common.i18n.Translator;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Log4j2
public class ExceptionHandlerAdvice {

	private static final String REQUIRED_BODY_MESSAGE = "Required request body is missing";
	private static final String ASSERT_TRUE = "AssertTrue";

	private final Translator translator;

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BadBusinessRequestException.class)
	ResponseBody<?> handle(BadBusinessRequestException ex, WebRequest request) {
		String message = translator.toLocale(ex.getCode(), ex.getDetails());
		return new ResponseBody<>(new ResponseMetadata(ex.getCode(), message, ex.getDetails()));
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(DataNotFoundException.class)
	ResponseBody<?> handle(DataNotFoundException ex, WebRequest request) {
		String message = translator.toLocale(ex.getCode(), ex.getDetails());
		return new ResponseBody<>(new ResponseMetadata(ex.getCode(), message, ex.getDetails()));
	}


	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	ResponseBody<?> handle(NoHandlerFoundException ex, WebRequest request) {
		return generalHandle(ex);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	ResponseBody<?> handle(HttpRequestMethodNotSupportedException ex, WebRequest request) {
		return generalHandle(ex);
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	ResponseBody<?> handle(AuthenticationException ex, WebRequest request) {
		return generalHandle(ex);
	}

	@ExceptionHandler(value = {
		ConstraintViolationException.class,
		MissingServletRequestParameterException.class,
		MissingServletRequestPartException.class,
		HttpMediaTypeNotSupportedException.class,
		UnsupportedSortPropertyException.class,
		ConversionFailedException.class,
		PropertyReferenceException.class,
		MultipartException.class,
	})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ResponseBody<?> handle(Exception ex, WebRequest request) {
		return generalHandle(ex);
	}

	private ResponseBody<?> generalHandle(Exception ex) {
		String code = extractErrorCode(ex.getClass().getSimpleName());
		return new ResponseBody<>(new ResponseMetadata(code, ex.getMessage()));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ResponseBody<?> handle(HttpMessageNotReadableException ex, WebRequest request) {
		Throwable cause = ex.getMostSpecificCause();
		String error = cause.getClass().getSimpleName();
		String code = extractErrorCode(error);
		String message = cause.getLocalizedMessage();
		if (cause instanceof JsonProcessingException) {
			message = ((JsonProcessingException) cause).getOriginalMessage();
		} else if (message.startsWith(REQUIRED_BODY_MESSAGE)) {
			message = REQUIRED_BODY_MESSAGE;
		}

		ResponseBody<?> response = new ResponseBody<>(new ResponseMetadata(code, message));
		return response;
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ResponseBody<?> handle(MethodArgumentTypeMismatchException ex, WebRequest request) {
		Throwable cause = ex.getMostSpecificCause();
		String error = cause.getClass().getSimpleName();
		String code = extractErrorCode(error);
		String message = cause.getLocalizedMessage();
		ResponseBody<?> response = new ResponseBody<>(new ResponseMetadata(code, message));
		return response;
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ResponseBody<?> handle(BindException ex, WebRequest request) {
		String code = extractErrorCode(ex.getClass().getSimpleName());
		String messages = ex.getFieldErrors().stream()
				.map(e -> {
					boolean needToTranslate = e.getDefaultMessage().contains(StringUtils.SPACE) == false;
					if (needToTranslate) {
						return translator.toLocale(e.getDefaultMessage());
					}
					if (ASSERT_TRUE.equals(e.getCode())) {
						return e.getDefaultMessage();
					}
					return String.format("%s %s", e.getField(), e.getDefaultMessage());
				})
				.collect(Collectors.joining("\n"));

		ResponseBody<?> response = new ResponseBody<>(new ResponseMetadata(code, messages, ex.getFieldErrors()));
		return response;
	}

	private String extractErrorCode(String exceptionName) {
		return exceptionName.substring(0, exceptionName.length() - 9).toUpperCase();
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ResponseBody<?> remainingHandle(Exception ex, WebRequest request) {
		log.error(ExceptionUtils.getStackTrace(ex));
		String code = HttpStatus.INTERNAL_SERVER_ERROR.name();
		String message = translator.toLocale(code);
		return new ResponseBody<>(new ResponseMetadata(code, message));
	}

}
