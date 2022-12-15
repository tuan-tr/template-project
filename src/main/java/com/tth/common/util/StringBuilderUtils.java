package com.tth.common.util;

public class StringBuilderUtils {

	public static StringBuilder append(StringBuilder builder, Object... objs) {
		for (Object e : objs) {
			builder.append(e);
		}
		return builder;
	}

}
