package com.tth.common.exception;

import java.util.Collection;

public class UnsupportedSortPropertyException extends RuntimeException {

	private static final String MESSAGE = "Unsupported sort property ";

	private Collection<String> properties;

	public UnsupportedSortPropertyException(Collection<String> properties) {
		this.properties = properties;
	}

	@Override
	public String getMessage() {
		return MESSAGE + properties;
	}

}
