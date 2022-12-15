package com.tth.baseproject.controller;

import java.time.OffsetDateTime;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.tth.baseproject.constant.Sortable;
import com.tth.baseproject.dto.user.UserCreateInput;
import com.tth.baseproject.dto.user.UserUpdateInput;
import com.tth.baseproject.service.UserService;
import com.tth.common.http.ResponseBodyProvider;
import com.tth.common.util.SortableProvider;
import com.tth.persistence.constant.UserStatus;
import com.tth.persistence.provider.filter.UserFilter;

import lombok.AllArgsConstructor;

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
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime effectiveFrom,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime effectiveTo,
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
				.effectiveFrom(effectiveFrom)
				.effectiveTo(effectiveTo)
				.createdFrom(createdFrom)
				.createdTo(createdTo)
				.keyword(keyword)
				.build();
		
		return responseProvider.ok(userService.searchPaging(filter, pageable));
	}

}
