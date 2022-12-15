package com.tth.baseproject.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tth.baseproject.service.context.UserContextService;
import com.tth.common.auth.UserContext;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

	private UserContextService userContextService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
	) throws ServletException, IOException {
		UserContext userContext = (UserContext) userContextService.loadUserByUsername("demo_username");
		if (userContext.isEnabled() == false) {
			throw new DisabledException("User is disabled");
		}

		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
				userContext, null, userContext.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		filterChain.doFilter(request, response);
	}

}
