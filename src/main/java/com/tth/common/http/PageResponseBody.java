package com.tth.common.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@Getter
public class PageResponseBody<T> {

	private PageResponse<T> data;

	public PageResponseBody(Page<T> page) {
		data = new PageResponse<>(page);
	}

	@Getter
	private class PageResponse<U> {
		private int page;
		private int size;
		private long totalElements;
		private int totalPages;
		private List<U> content;
		
		public PageResponse(Page<U> page) {
			this.page = page.getNumber();
			this.size = page.getSize();
			this.totalElements = page.getTotalElements();
			this.totalPages = page.getTotalPages();
			this.content = page.getContent();
		}
	}

}
