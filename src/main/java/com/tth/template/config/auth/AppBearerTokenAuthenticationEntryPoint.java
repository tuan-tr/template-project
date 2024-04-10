package com.tth.template.config.auth;

import com.tth.common.http.FailureResponseBody;
import com.tth.common.jackson.JsonParserProvider;
import com.tth.common.util.ExceptionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Clone from {@link BearerTokenAuthenticationEntryPoint}
 */
@Component
@RequiredArgsConstructor
public final class AppBearerTokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final JsonParserProvider jsonParserProvider;
	private String realmName;

	/**
	 * Collect error details from the provided parameters and format according to RFC
	 * 6750, specifically {@code error}, {@code error_description}, {@code error_uri}, and
	 * {@code scope}.
	 * @param request that resulted in an <code>AuthenticationException</code>
	 * @param response so that the user agent can begin authentication
	 * @param authException that caused the invocation
	 * @throws IOException 
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		Map<String, String> parameters = new LinkedHashMap<>();
		if (this.realmName != null) {
			parameters.put("realm", this.realmName);
		}
		if (authException instanceof OAuth2AuthenticationException) {
			OAuth2Error error = ((OAuth2AuthenticationException) authException).getError();
			parameters.put("error", error.getErrorCode());
			if (StringUtils.hasText(error.getDescription())) {
				parameters.put("error_description", error.getDescription());
			}
			if (StringUtils.hasText(error.getUri())) {
				parameters.put("error_uri", error.getUri());
			}
			if (error instanceof BearerTokenError bearerTokenError) {
				if (StringUtils.hasText(bearerTokenError.getScope())) {
					parameters.put("scope", bearerTokenError.getScope());
				}
				status = ((BearerTokenError) error).getHttpStatus();
			}
		}
		String wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters);
		response.addHeader(HttpHeaders.WWW_AUTHENTICATE, wwwAuthenticate);
		response.setStatus(status.value());
		
		String code = ExceptionUtils.extractErrorCode(authException);
		FailureResponseBody body = new FailureResponseBody(code, authException.getMessage());
		response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(jsonParserProvider.toString(body));
	}

	/**
	 * Set the default realm name to use in the bearer token error response
	 * @param realmName
	 */
	public void setRealmName(String realmName) {
		this.realmName = realmName;
	}

	private static String computeWWWAuthenticateHeaderValue(Map<String, String> parameters) {
		StringBuilder wwwAuthenticate = new StringBuilder();
		wwwAuthenticate.append("Bearer");
		if (!parameters.isEmpty()) {
			wwwAuthenticate.append(" ");
			int i = 0;
			for (Map.Entry<String, String> entry : parameters.entrySet()) {
				wwwAuthenticate.append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
				if (i != parameters.size() - 1) {
					wwwAuthenticate.append(", ");
				}
				i++;
			}
		}
		return wwwAuthenticate.toString();
	}

}
