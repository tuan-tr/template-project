package com.tth.common.http;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PageRequestProvider {

	private final SpringDataWebProperties springDataWebProperties;

	public Pageable convert(PageRequestParam param) {
		int page = param.getPage() == null ? 0 : param.getPage();
		int size = param.getSize() == null ? springDataWebProperties.getPageable().getDefaultPageSize() : param.getSize();
		Sort sort;
		
		if (CollectionUtils.isEmpty(param.getSort())) {
			sort = Sort.unsorted();
		} else {
			List<Order> orders = param.getSort().stream()
					.filter(StringUtils::isNotBlank)
					.map(e -> {
						String[] sortParams = e.split(",");
						if (sortParams.length > 1 && Direction.fromOptionalString(sortParams[1]).isPresent()) {
							return new Order(Direction.fromString(sortParams[1]), sortParams[0]);
						}
						return new Order(Direction.ASC, sortParams[0]);
					})
					.collect(Collectors.toList());
			
			sort = Sort.by(orders);
		}
		
		return PageRequest.of(page, size, sort);
	}

}
