package com.tth.common.util;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Sort;

import com.tth.common.exception.UnsupportedSortFieldException;

public class SortableProvider {

	public static void validate(Sort sort, Collection<String> sortableFields) {
		List<String> inputFields = sort.stream().map(e -> e.getProperty()).collect(Collectors.toList());
		Collection<String> unsupportedFields = CollectionUtils.removeAll(inputFields, sortableFields);
		
		if (CollectionUtils.isNotEmpty(unsupportedFields)) {
			throw new UnsupportedSortFieldException(unsupportedFields);
		}
	}

}
