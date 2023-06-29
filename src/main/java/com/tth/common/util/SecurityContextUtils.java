package com.tth.common.util;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public class SecurityContextUtils {

	public static Optional<UserDetails> findCurrent() {
		return Optional.ofNullable(SecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.filter(Authentication::isAuthenticated)
				.map(Authentication::getPrincipal)
				.filter(UserDetails.class::isInstance)
				.map(UserDetails.class::cast);
	}

	public static UserDetails getCurrent() {
		return findCurrent()
				.orElseThrow(() -> new AuthenticationCredentialsNotFoundException(
						"No current user associated with this request"));
	}

}
