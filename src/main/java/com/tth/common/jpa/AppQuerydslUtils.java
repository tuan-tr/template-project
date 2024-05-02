package com.tth.common.jpa;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Sort;

public class AppQuerydslUtils {

	public static <T> void setOrder(JPAQuery<T> query, Path<T> entityPath, Sort sort) {
		if (sort != null && sort.isSorted()) {
			sort.forEach(e -> {
				Order order = e.getDirection().isAscending() ? Order.ASC : Order.DESC;
				Path<Object> fieldPath = Expressions.path(Object.class, entityPath, e.getProperty());     
				query.orderBy(new OrderSpecifier(order, fieldPath));
			});
		}
	}

}
