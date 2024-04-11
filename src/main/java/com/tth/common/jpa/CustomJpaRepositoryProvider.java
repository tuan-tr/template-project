package com.tth.common.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CustomJpaRepositoryProvider {

	@PersistenceContext
	private EntityManager em;

	public String makeOrderClause(Sort sort, String tableAlias) {
		String orderClause = "";
		if (sort != null && sort.isSorted()) {
			String order = sort.stream()
					.map(e -> String.format("%s.%s %s", tableAlias, e.getProperty(), e.getDirection()))
					.collect(Collectors.joining(", "));
			orderClause = " ORDER BY " + order;
		}
		return orderClause;
	}

	public <T> Page<T> findPaging(String countSql, String selectSql, Pageable pageable, Map<String, Object> parameterMap, Class<T> type) {
		TypedQuery<Long> countQuery = em.createQuery(countSql, Long.class);
		setParameters(countQuery, parameterMap);
		Long total = countQuery.getSingleResult();
		
		if (total == 0) {
			return new PageImpl<>(Collections.emptyList(), pageable, 0);
		}
		
		TypedQuery<T> pagingQuery = em.createQuery(selectSql, type)
				.setFirstResult((int) pageable.getOffset())
				.setMaxResults(pageable.getPageSize());
		
		setParameters(pagingQuery, parameterMap);
		List<T> entities = pagingQuery.getResultList();
		return new PageImpl<>(entities, pageable, total);
	}

	private void setParameters(TypedQuery<?> query, Map<String, Object> parameterMap) {
		parameterMap.entrySet().forEach(e -> {
			query.setParameter(e.getKey(), e.getValue());
		});
	}

}
