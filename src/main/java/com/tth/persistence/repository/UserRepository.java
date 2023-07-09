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

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Validated
public interface UserRepository extends JpaRepository<User, String>, UserCustomRepository,
		QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

	@Override
	default void customize(QuerydslBindings bindings, QUser qType) {
		// bindings.bind(qType.id).first((path, value) -> path.eq(value));
		bindings.bind(qType.id).all((path, values) -> Optional.of(path.in(values)));
		bindings.bind(qType.status).all((path, values) -> Optional.of(path.in(values)));
		bindings.bind(qType.updatedAt).all((path, values) -> {
			Iterator<? extends OffsetDateTime> iterator = values.iterator();
			if (values.size() == 1) {
				return Optional.of(path.goe(iterator.next()));
			}
			return Optional.of(path.between(iterator.next(), iterator.next()));
		});
		bindings.bind(String.class).first((StringPath path, String value) -> path.containsIgnoreCase(value));
		bindings.excluding(qType.version);
	}

	List<User> findByIdIn(@NotEmpty Collection<String> ids);

}
