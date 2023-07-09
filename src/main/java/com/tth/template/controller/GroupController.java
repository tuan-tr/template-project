package com.tth.template.controller;

import com.querydsl.core.types.Predicate;
import com.tth.common.http.ResponseBodyProvider;
import com.tth.persistence.entity.Group;
import com.tth.persistence.entity.UserGroup;
import com.tth.template.constant.Sortable;
import com.tth.template.dto.group.GroupAddUserInput;
import com.tth.template.dto.group.GroupCreateInput;
import com.tth.template.dto.group.GroupRemoveUserInput;
import com.tth.template.dto.group.GroupUpdateInput;
import com.tth.template.service.GroupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "group")
@AllArgsConstructor
public class GroupController {

	private ResponseBodyProvider responseProvider;
	private GroupService groupService;

	@PostMapping
	public Object create(@RequestBody @Valid GroupCreateInput input) {
		return responseProvider.ok(groupService.create(input));
	}

	@PutMapping("{id}")
	public Object update(@PathVariable String id, @RequestBody @Valid GroupUpdateInput input) {
		groupService.update(id, input);
		return responseProvider.ok();
	}

	@PostMapping("{id}/user")
	public Object addUsers(@PathVariable String id, @RequestBody @Valid GroupAddUserInput input) {
		groupService.addUsers(id, input);
		return responseProvider.ok();
	}

	@DeleteMapping("{id}/user")
	public Object removeUsers(@PathVariable String id, @RequestBody @Valid GroupRemoveUserInput input) {
		groupService.removeUsers(id, input);
		return responseProvider.ok();
	}

	@GetMapping("{id}")
	public Object get(@PathVariable String id) {
		return responseProvider.ok(groupService.getDetail(id));
	}

	@GetMapping("querydsl")
	public Object search(
			@QuerydslPredicate(root = Group.class) Predicate predicate,
			@SortDefault(sort = Sortable.UPDATED_AT, direction = Sort.Direction.DESC) Pageable pageable
	) {
		return responseProvider.ok(groupService.search(predicate, pageable));
	}

	@GetMapping("{id}/user/querydsl")
	public Object searchUser(@PathVariable String id,
			@QuerydslPredicate(root = UserGroup.class) Predicate predicate,
			@SortDefault(sort = Sortable.UPDATED_AT, direction = Sort.Direction.DESC) Pageable pageable
	) {
		return responseProvider.ok(groupService.searchUser(id, predicate, pageable));
	}

}
