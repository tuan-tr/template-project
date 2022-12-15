package com.tth.common.exception;

import java.util.Collection;

public class UnsupportedSortFieldException extends RuntimeException {

	private static final String MESSAGE = "Unsupported sort field ";

	private Collection<String> fields;

	public UnsupportedSortFieldException(Collection<String> fields) {
		this.fields = fields;
	}

	@Override
	public String getMessage() {
		return MESSAGE + fields;
	}

}
