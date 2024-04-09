package com.tth.common.http;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SuccessfulResponseBody<T> {

	private T data;

}
