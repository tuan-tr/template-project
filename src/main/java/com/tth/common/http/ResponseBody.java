package com.tth.common.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseBody<T> {

	private ResponseMetadata metadata;
	private T data;

	public ResponseBody (ResponseMetadata metadata) {
		this.metadata = metadata;
	}

}
