package com.tth.common.servletfilter;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tth.baseproject.service.context.UserContextService;
import com.tth.common.auth.TokenPayload;
import com.tth.common.auth.UserContext;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private ObjectMapper objectMapper;
	private UserContextService userContextService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
	) throws ServletException, IOException {
		String bearerToken[] = null;
		String authToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.isNoneBlank(authToken) && authToken.startsWith("Bearer ")) {
			bearerToken = authToken.substring(7).split("\\.");
		}

		if (bearerToken != null && bearerToken.length == 3) {
			String tokenPayload = bearerToken[1];

			byte[] tokenPayloadByte = Base64.getDecoder().decode(tokenPayload);
			String decodedPayload = new String(tokenPayloadByte);
			TokenPayload payload = objectMapper.readValue(decodedPayload, TokenPayload.class);

			UserContext userContext = (UserContext) userContextService.loadUserByUsername(payload.getUsername());
			if (userContext.isEnabled() == false) {
				throw new DisabledException("User is disabled");
			}

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					userContext, null, userContext.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

}
