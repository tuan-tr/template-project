package com.tth.common.http;

import java.util.Map;

import javax.annotation.Nullable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.tth.common.i18n.Translator;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ResponseBodyProvider {

	private static final String OK = "OK";

	private Translator translator;

	public Object ok() {
		return ok(OK, null, null, null);
	}

	public Object ok(Object data) {
		return ok(OK, null, data, null);
	}

	public Object ok(String translationCode, Object data) {
		return ok(translationCode, null, data, null);
	}

	public Object ok(String translationCode, Map<String, ?> translationArgMap, Object data) {
		return ok(translationCode, translationArgMap, data, null);
	}

	public Object ok(Object data, String message) {
		return ok(OK, null, data, message);
	}

	public Object ok(String translationCode, @Nullable Map<String, ?> translationArgMap, Object data, String message) {
		boolean needTranslate = StringUtils.isBlank(message);
		if (needTranslate) {
			message = translator.toLocale(translationCode, translationArgMap);
		}
		
		return new ResponseBody<>(new ResponseMetadata(translationCode, message, translationArgMap), data);
	}

}
