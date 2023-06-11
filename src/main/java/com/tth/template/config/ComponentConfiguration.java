package com.tth.template.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.tth.common.auth.AuthEnviroment;
import com.tth.common.servletfilter.RequestIdHandlerFilter;

@Configuration
@EntityScan(basePackages = "com.tth.persistence.entity")
@EnableJpaRepositories(basePackages = "com.tth.persistence.repository")
@ConfigurationPropertiesScan(basePackageClasses = {
		AuthEnviroment.class
})
@ComponentScan(basePackages = {
		"com.tth.common.auth",
		"com.tth.common.exception",
		"com.tth.common.http",
		"com.tth.common.i18n",
		"com.tth.common.jackson",
		"com.tth.common.jpa",
		"com.tth.common.log",
		"com.tth.common.openapi",
}, basePackageClasses = {
	RequestIdHandlerFilter.class
})
public class ComponentConfiguration {

}
