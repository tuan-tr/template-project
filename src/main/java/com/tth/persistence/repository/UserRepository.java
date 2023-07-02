package com.tth.persistence.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.tth.persistence.entity.QUser;
import com.tth.persistence.entity.User;
import com.tth.persistence.repository.custom.UserCustomRepository;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;

@Validated
public interface UserRepository extends JpaRepository<User, String>, UserCustomRepository,
		QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

	List<User> findByIdIn(@NotEmpty Collection<String> ids);

	@Override
	default void customize(QuerydslBindings bindings, QUser qType) {
		bindings.bind(qType.id).first((path, value) -> path.eq(value));
		bindings.bind(qType.status).first((path, value) -> path.eq(value));
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
		bindings.excluding(qType.version);
	}


}
