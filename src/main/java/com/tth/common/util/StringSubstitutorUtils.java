package com.tth.common.util;

import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

public class StringSubstitutorUtils {
	
	public static StringSubstitutor defaultInstance(Map<String,?> argMap) {
		return new StringSubstitutor(argMap, "@{", "}");
	}
	
}
