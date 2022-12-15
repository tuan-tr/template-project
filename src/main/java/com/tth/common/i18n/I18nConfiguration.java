package com.tth.common.i18n;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

@Configuration
public class I18nConfiguration {

	@Value("${spring.web.locale}")
	private String defaultLanguage;

	@Value("${spring.web.supported-locales}")
	private String[] supportedLanguages;

	@Bean
	LocaleResolver localeResolver() {
		List<Locale> supportedLocales = Stream.of(supportedLanguages)
				.map(e -> new Locale(e))
				.collect(Collectors.toList());
		
		AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
		resolver.setDefaultLocale(new Locale(defaultLanguage));
		resolver.setSupportedLocales(supportedLocales);
		return resolver;
	}

	@Bean
	MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

}
