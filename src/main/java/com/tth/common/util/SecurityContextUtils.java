package com.tth.common.util;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tth.common.auth.UserContext;

public class SecurityContextUtils {

	public static Optional<UserContext> findCurrent() {
		return Optional.ofNullable(SecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.filter(Authentication::isAuthenticated)
				.map(Authentication::getPrincipal)
				.filter(UserContext.class::isInstance)
				.map(UserContext.class::cast);
	}

	public static UserContext getCurrent() {
		return findCurrent()
				.orElseThrow(() -> new AuthenticationCredentialsNotFoundException(
						"No current user associated with this request"));
	}

}
