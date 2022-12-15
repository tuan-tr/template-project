package com.tth.common.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthenticationExceptionEntryPoint implements AuthenticationEntryPoint {

	private HandlerExceptionResolver handlerExceptionResolver;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception
	) throws IOException, ServletException {
		handlerExceptionResolver.resolveException(request, response, null, exception);
	}

}
