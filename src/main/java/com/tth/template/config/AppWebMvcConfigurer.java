package com.tth.template.config;

import com.tth.common.auth.AuthEnviroment;
import com.tth.template.config.webmvc.RequestContextHandlerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AppWebMvcConfigurer implements WebMvcConfigurer {
	private final RequestContextHandlerInterceptor requestContextHandlerInterceptor;
	private final AuthEnviroment authEnv;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(requestContextHandlerInterceptor)
				.excludePathPatterns(authEnv.getNonAuthenticatedPaths());
	}

}
