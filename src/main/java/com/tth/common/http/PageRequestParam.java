package com.tth.common.http;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PageRequestParam {

	@Min(0)
	private Integer page;

	@Min(1)
	private Integer size;

	private Collection<String> sort;

}
