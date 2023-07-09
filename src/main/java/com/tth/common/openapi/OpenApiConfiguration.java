package com.tth.common.openapi;

import java.util.List;

import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
// @SecurityScheme(name = "bearerToken", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
@OpenAPIDefinition(info = @Info(title = "APIs", version = "0.0.1")
// , security = { @SecurityRequirement(name = "bearerToken") }
)
public class OpenApiConfiguration {

	@Value("${server.external-mapping-url:}")
	private String serverUrl;

	@Value("${spring.web.locale}")
	private String defaultLanguage;

	// @Bean
	ModelResolver modelResolver(ObjectMapper objectMapper) {
		return new ModelResolver(objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE));
	}

	@Bean
	OperationCustomizer customize() {
		return (operation, handlerMethod) -> operation.addParametersItem(
				new Parameter()
						.in("header")
						.name(HttpHeaders.ACCEPT_LANGUAGE)
						.required(false)
						.example(defaultLanguage));
	}

	// @Bean
	OpenAPI customOpenAPI() {
		Server server = new Server().url(this.serverUrl);
		return new OpenAPI()
				.servers(List.of(server));
	}

}
