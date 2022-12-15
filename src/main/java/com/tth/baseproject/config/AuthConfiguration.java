package com.tth.baseproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tth.common.auth.AuthEnviroment;
import com.tth.common.auth.AuthenticationExceptionEntryPoint;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class AuthConfiguration {

	private AuthEnviroment authEnv;
	private AuthenticationFilter authenticationFilter;
	private AuthenticationExceptionEntryPoint authenticationExceptionEntryPoint;

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration
	) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().disable()
				.csrf().disable()
				.httpBasic().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers(authEnv.getNonAuthenticatedPaths()).permitAll()
				.anyRequest().authenticated()
				.and()
				.exceptionHandling().authenticationEntryPoint(authenticationExceptionEntryPoint)
				.and()
				.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new ExceptionTranslationFilter(authenticationExceptionEntryPoint),
						authenticationFilter.getClass()) // indicates EntryPoint handle AuthenticationException from Filter
		;
		return http.build();
	}

}
