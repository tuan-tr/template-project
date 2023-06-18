package com.tth.common.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth")
@AllArgsConstructor
@Getter
public final class AuthEnviroment {

	private final String[] nonAuthenticatedPaths;
	private final String[] corsAllowedOrigins;

}
