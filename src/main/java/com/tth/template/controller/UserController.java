package com.tth.template.controller;

import com.querydsl.core.types.Predicate;
import com.tth.common.http.ResponseBodyProvider;
import com.tth.common.util.SortableProvider;
import com.tth.persistence.constant.UserStatus;
import com.tth.persistence.entity.User;
import com.tth.persistence.provider.filter.UserFilter;
import com.tth.template.constant.Sortable;
import com.tth.template.dto.user.UserCreateInput;
import com.tth.template.dto.user.UserUpdateInput;
import com.tth.template.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Set;

@RestController
@RequestMapping(value = "user")
@AllArgsConstructor
public class UserController {

	private ResponseBodyProvider responseProvider;
	private UserService userService;

	@PostMapping
	public Object post(@RequestBody @Valid UserCreateInput input) {
		return responseProvider.ok(userService.create(input));
	}

	@PutMapping("{id}")
	public Object post(@PathVariable String id, @RequestBody @Valid UserUpdateInput input) {
		userService.update(id, input);
		return responseProvider.ok();
	}

	@GetMapping("{id}")
	public Object get(@PathVariable String id) {
		return responseProvider.ok(userService.getDetail(id));
	}

	@GetMapping
	public Object searchPaging(
			@RequestParam(required = false) Set<String> ids,
			@RequestParam(required = false) Set<UserStatus> statuses,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime effectiveStart,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime effectiveEnd,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime createdFrom,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime createdTo,
			@RequestParam(required = false) String keyword,
			@SortDefault(sort = Sortable.CREATED_AT, direction = Sort.Direction.DESC) Pageable pageable
	) {
		SortableProvider.validate(pageable.getSort(), Sortable.USER);
		UserFilter filter = UserFilter.builder()
				.ids(ids)
				.statuses(statuses)
				.name(name)
				.effectiveStart(effectiveStart)
				.effectiveEnd(effectiveEnd)
				.createdFrom(createdFrom)
				.createdTo(createdTo)
				.keyword(keyword)
				.build();
		
		return responseProvider.ok(userService.searchPaging(filter, pageable));
	}

	@GetMapping("querydsl")
	public Object search(
			@QuerydslPredicate(root = User.class) Predicate predicate,
			@SortDefault(sort = Sortable.CREATED_AT, direction = Sort.Direction.DESC) Pageable pageable
	) {
		SortableProvider.validate(pageable.getSort(), Sortable.USER);
		return responseProvider.ok(userService.search(predicate, pageable));
	}

}
