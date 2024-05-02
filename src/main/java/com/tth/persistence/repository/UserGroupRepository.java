package com.tth.persistence.repository;

import com.querydsl.core.types.dsl.StringPath;
import com.tth.persistence.entity.QUserGroup;
import com.tth.persistence.entity.UserGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.validation.annotation.Validated;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Validated
public interface UserGroupRepository extends JpaRepository<UserGroup, String>, 
		QuerydslPredicateExecutor<UserGroup>, QuerydslBinderCustomizer<QUserGroup> {

	@Override
	default void customize(QuerydslBindings bindings, QUserGroup qType) {
		bindings.bind(qType.groupId).first((path, value) -> path.eq(value));
		bindings.bind(qType.userId).all((path, values) -> Optional.of(path.in(values)));
		bindings.bind(qType.user.status).all((path, values) -> Optional.of(path.in(values)));
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
		bindings.excluding(qType.user.version);
	}

	List<UserGroup> findByGroupIdAndUserIdIn(@NotBlank String groupId, @NotEmpty Collection<String> userIds);

}
