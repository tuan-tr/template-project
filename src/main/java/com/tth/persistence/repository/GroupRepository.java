package com.tth.persistence.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.tth.persistence.entity.QGroup;
import com.tth.persistence.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface GroupRepository extends JpaRepository<Group, String>,
		QuerydslPredicateExecutor<Group>, QuerydslBinderCustomizer<QGroup> {

	@Override
	default void customize(QuerydslBindings bindings, QGroup qType) {
		bindings.bind(qType.id).first((path, value) -> path.eq(value));
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

}
