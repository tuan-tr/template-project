package com.tth.common.i18n;

import java.util.Locale;
import java.util.Map;

import javax.annotation.Nullable;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Translator {

	private MessageSource messageSource;

	public String toLocale(String code) {
		return toLocale(code, null);
	}

	public String toLocale(String code, @Nullable Map<String, ?> argMap) {
		String message;
		Locale locale = LocaleContextHolder.getLocale();
		String template = messageSource.getMessage(code, null, locale);
		if (MapUtils.isEmpty(argMap)) {
			message = template;
		} else {
			message = new StringSubstitutor(argMap).replace(template);
		}

		return message;
	}

}
