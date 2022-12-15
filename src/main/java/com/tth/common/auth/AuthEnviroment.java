package com.tth.common.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import lombok.AllArgsConstructor;
import lombok.Getter;

@ConfigurationProperties(prefix = "auth")
@ConstructorBinding
@AllArgsConstructor
@Getter
public final class AuthEnviroment {

	private final String[] nonAuthenticatedPaths;

}
