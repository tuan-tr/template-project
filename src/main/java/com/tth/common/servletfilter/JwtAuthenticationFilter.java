package com.tth.common.servletfilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tth.common.auth.TokenPayload;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper;

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

			UserDetails userDetails = User.withUsername(payload.getUsername())
					.password("")
					.accountExpired(false)
					.accountLocked(false)
					.credentialsExpired(false)
					.disabled(false)
					.build();
			
			if (userDetails.isEnabled() == false) {
				throw new DisabledException("User is disabled");
			}

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

}
