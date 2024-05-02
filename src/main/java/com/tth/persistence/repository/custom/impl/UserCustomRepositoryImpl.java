package com.tth.persistence.repository.custom.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tth.common.jpa.AppQuerydslUtils;
import com.tth.persistence.entity.QUser;
import com.tth.persistence.entity.User;
import com.tth.persistence.provider.filter.UserFilter;
import com.tth.persistence.repository.custom.UserCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

	@PersistenceContext
	private final EntityManager em;
	
	@Override
	public Page<User> findPaging(UserFilter filter, Pageable pageable) {
		List<User> entities;
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		QUser user = QUser.user;
		JPAQuery<User> selectQuery = queryFactory.selectFrom(user);
		JPAQuery<Long> countQuery = queryFactory.select(user.count()).from(user);
		
		if (CollectionUtils.isNotEmpty(filter.getIds())) {
			selectQuery.where(user.id.in(filter.getIds()));
			countQuery.where(user.id.in(filter.getIds()));
		}
		if (CollectionUtils.isNotEmpty(filter.getStatuses())) {
			selectQuery.where(user.status.in(filter.getStatuses()));
			countQuery.where(user.status.in(filter.getStatuses()));
		}
		if (StringUtils.isNotBlank(filter.getName())) {
			selectQuery.where(user.name.like("%" + filter.getName() + "%"));
			countQuery.where(user.name.like("%" + filter.getName() + "%"));
		}
		if (filter.getEffectiveStart() != null) {
			selectQuery.where(user.effectiveEnd.goe(filter.getEffectiveStart()));
			countQuery.where(user.effectiveEnd.goe(filter.getEffectiveStart()));
		}
		if (filter.getEffectiveEnd() != null) {
			selectQuery.where(user.effectiveStart.loe(filter.getEffectiveEnd()));
			countQuery.where(user.effectiveStart.loe(filter.getEffectiveEnd()));
		}
		if (filter.getCreatedFrom() != null) {
			selectQuery.where(user.createdAt.goe(filter.getCreatedFrom()));
			countQuery.where(user.createdAt.goe(filter.getCreatedFrom()));
		}
		if (filter.getCreatedTo() != null) {
			selectQuery.where(user.createdAt.loe(filter.getCreatedTo()));
			countQuery.where(user.createdAt.loe(filter.getCreatedTo()));
		}
		if (StringUtils.isNotBlank(filter.getKeyword())) {
			selectQuery.where(user.id.like("%" + filter.getKeyword() + "%").or(user.name.like("%" + filter.getKeyword() + "%")));
			countQuery.where(user.id.like("%" + filter.getKeyword() + "%").or(user.name.like("%" + filter.getKeyword() + "%")));
		}
		
		AppQuerydslUtils.setOrder(selectQuery, user, pageable.getSort());
		
		entities = selectQuery.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		if (CollectionUtils.isEmpty(entities)) {
			return new PageImpl<>(entities, pageable, 0);
		} else {
			long total = countQuery.fetchFirst();
			return new PageImpl<>(entities, pageable, total);
		}
	}

}
