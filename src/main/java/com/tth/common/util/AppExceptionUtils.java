package com.tth.common.util;

import com.google.common.base.CaseFormat;

public class AppExceptionUtils {

	public static String extractErrorCode(Throwable exception) {
		String exceptionName = exception.getClass().getSimpleName();
		String error = exceptionName.substring(0, exceptionName.length() - 9);
		return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, error);
	}

}
