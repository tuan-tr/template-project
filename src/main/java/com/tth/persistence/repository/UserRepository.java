package com.tth.persistence.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.tth.persistence.entity.QUser;
import com.tth.persistence.entity.User;
import com.tth.persistence.repository.custom.UserCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface UserRepository extends JpaRepository<User, String>, UserCustomRepository,
		QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

	@Override
	default void customize(QuerydslBindings bindings, QUser user) {
		bindings.bind(user.id).first((path, value) -> path.eq(value));
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
		bindings.excluding(user.version);
	}

}
