package com.tth.persistence.repository.custom.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tth.common.jpa.FindPagingJpaProvider;
import com.tth.persistence.entity.User;
import com.tth.persistence.provider.filter.UserFilter;
import com.tth.persistence.repository.custom.UserCustomRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

	private FindPagingJpaProvider findPagingJpaProvider;

	@Override
	public Page<User> findPaging(UserFilter filter, Pageable pageable) {
		StringBuilder countSqlBuilder = new StringBuilder(100)
				.append("SELECT COUNT(u.id) FROM User u");
		StringBuilder selectSqlBuilder = new StringBuilder(100)
				.append("SELECT u FROM User u");
		StringBuilder conditionJoinClauseSqlBuilder = new StringBuilder();
		StringBuilder whereClauseSqlBuilder = new StringBuilder(50)
				.append(" WHERE 1=1");
		Map<String, Object> parameterMap = new LinkedHashMap<>();
		
		if (CollectionUtils.isNotEmpty(filter.getIds())) {
			whereClauseSqlBuilder.append(" AND u.id IN (:ids)");
			parameterMap.put("ids", filter.getIds());
		}
		if (CollectionUtils.isNotEmpty(filter.getStatuses())) {
			whereClauseSqlBuilder.append(" AND u.status IN (:statuses)");
			parameterMap.put("statuses", filter.getStatuses());
		}
		if (StringUtils.isNotBlank(filter.getName())) {
			whereClauseSqlBuilder.append(" AND u.name LIKE :name");
			parameterMap.put("name", "%" + filter.getName() + "%");
		}
		if (filter.getEffectiveStart() != null) {
			whereClauseSqlBuilder.append(" AND u.effectiveEnd >= :effectiveStart");
			parameterMap.put("effectiveStart", filter.getEffectiveStart());
		}
		if (filter.getEffectiveEnd() != null) {
			whereClauseSqlBuilder.append(" AND u.effectiveStart <= :effectiveEnd");
			parameterMap.put("effectiveEnd", filter.getEffectiveEnd());
		}
		if (filter.getCreatedFrom() != null) {
			whereClauseSqlBuilder.append(" AND u.createdAt >= :createdFrom");
			parameterMap.put("createdFrom", filter.getCreatedFrom());
		}
		if (filter.getCreatedTo() != null) {
			whereClauseSqlBuilder.append(" AND u.createdAt <= :createdTo");
			parameterMap.put("createdTo", filter.getCreatedTo());
		}
		if (StringUtils.isNotBlank(filter.getKeyword())) {
			whereClauseSqlBuilder.append(" AND (u.code = :keywordId OR u.name LIKE :keyword)");
			parameterMap.put("keywordId", filter.getKeyword());
			parameterMap.put("keyword", "%" + filter.getKeyword() + "%");
		}
		
		String orderClause = findPagingJpaProvider.makeOrderClause(pageable.getSort(), "u");
		
		countSqlBuilder
				.append(conditionJoinClauseSqlBuilder)
				.append(whereClauseSqlBuilder);
		
		selectSqlBuilder
				.append(conditionJoinClauseSqlBuilder)
				.append(whereClauseSqlBuilder)
				.append(orderClause);
		
		return findPagingJpaProvider.findPaging(countSqlBuilder.toString(), selectSqlBuilder.toString(), pageable, parameterMap, User.class);
	}

}
