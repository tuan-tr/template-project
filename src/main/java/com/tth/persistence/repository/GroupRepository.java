package com.tth.persistence.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.tth.persistence.entity.Group;
import com.tth.persistence.entity.QGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Validated
public interface GroupRepository extends JpaRepository<Group, String>,
		QuerydslPredicateExecutor<Group>, QuerydslBinderCustomizer<QGroup> {

	@Override
	default void customize(QuerydslBindings bindings, QGroup qType) {
		bindings.bind(qType.id).all((path, values) -> Optional.of(path.in(values)));
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
	}

}
