package com.tth.baseproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tth.common.log.GetRequestLogger;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class CustomWebMvcConfigurer implements WebMvcConfigurer {

	private GetRequestLogger getRequestLogger;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getRequestLogger);
	}

}
