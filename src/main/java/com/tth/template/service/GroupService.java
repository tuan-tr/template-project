package com.tth.template.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.tth.common.exception.BadBusinessRequestException;
import com.tth.common.exception.DataNotFoundException;
import com.tth.persistence.constant.GroupStatus;
import com.tth.persistence.entity.Group;
import com.tth.persistence.entity.QUserGroup;
import com.tth.persistence.entity.User;
import com.tth.persistence.entity.UserGroup;
import com.tth.persistence.repository.GroupRepository;
import com.tth.persistence.repository.UserGroupRepository;
import com.tth.persistence.repository.UserRepository;
import com.tth.template.dto.group.GroupAddUserInput;
import com.tth.template.dto.group.GroupCreateInput;
import com.tth.template.dto.group.GroupDto;
import com.tth.template.dto.group.GroupRemoveUserInput;
import com.tth.template.dto.group.GroupUpdateInput;
import com.tth.template.dto.group.UserGroupDto;
import com.tth.template.exception.ErrorCode;
import com.tth.template.projector.GroupProjector;
import com.tth.template.projector.UserGroupProjector;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

	private final GroupRepository groupRepo;
	private final UserGroupRepository userGroupRepo;
	private final UserRepository userRepo;

	public Group getEntityOrThrowBadRequest(String id) {
		return groupRepo.findById(id)
				.orElseThrow(() -> new BadBusinessRequestException(ErrorCode.GROUP_NOT_FOUND.name(),
						Map.of("id", id)));
	}

	@Transactional
	public GroupDto create(GroupCreateInput input) {
		Group entity = Group.builder()
				.status(GroupStatus.ACTIVE)
				.name(input.getName())
				.build();
		
		if (CollectionUtils.isNotEmpty(input.getUserIds())) {
			List<User> users = userRepo.findByIdIn(input.getUserIds());
			List<UserGroup> userGroups = users.stream()
					.map(e -> UserGroup.builder()
							.user(e)
							.group(entity)
							.build())
					.collect(Collectors.toList());
			
			entity.setUserGroups(userGroups);
		}
		
		groupRepo.save(entity);
		return GroupDto.builder()
				.id(entity.getId())
				.build();
	}

	@Transactional
	public void update(String id, GroupUpdateInput input) {
		Group entity = getEntityOrThrowBadRequest(id);
		entity.setStatus(input.getStatus());
		entity.setName(input.getName());
	}

	@Transactional
	public void addUsers(String id, GroupAddUserInput input) {
		Group entity = getEntityOrThrowBadRequest(id);
		
		List<UserGroup> existingUserGroups = userGroupRepo.findByGroupIdAndUserIdIn(id, input.getUserIds());
		Set<String> existingUserIds = existingUserGroups.stream().map(e -> e.getUserId()).collect(Collectors.toSet());
		
		Collection<String> newUserIds = CollectionUtils.removeAll(input.getUserIds(), existingUserIds);
		List<User> users = userRepo.findByIdIn(newUserIds);
		
		List<UserGroup> userGroups = users.stream()
				.map(e -> UserGroup.builder()
						.user(e)
						.group(entity)
						.build())
				.collect(Collectors.toList());
		
		userGroupRepo.saveAll(userGroups);
	}

	@Transactional
	public void removeUsers(String id, GroupRemoveUserInput input) {
		getEntityOrThrowBadRequest(id);
		
		List<UserGroup> userGroups = userGroupRepo.findByGroupIdAndUserIdIn(id, input.getUserIds());
		
		userGroupRepo.deleteAll(userGroups);
	}

	@Transactional(readOnly = true)
	public GroupDto getDetail(String id) {
		Group entity = groupRepo.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.GROUP_NOT_FOUND.name(),
						Map.of("id", id)));
		
		GroupDto dto = GroupProjector.toDetailDto(entity);
		return dto;
	}

	@Transactional(readOnly = true)
	public Page<GroupDto> search(Predicate predicate, Pageable pageable) {
		Page<Group> page = groupRepo.findAll(predicate, pageable);
		List<GroupDto> content = GroupProjector.toSearchDto(page.getContent());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@Transactional(readOnly = true)
	public Page<UserGroupDto> searchUser(String groupId, Predicate predicate, Pageable pageable) {
		QUserGroup qType = QUserGroup.userGroup;
		BooleanBuilder booleanBuilder = new BooleanBuilder()
				.and(qType.groupId.eq(groupId))
				.and(predicate);
		
		Page<UserGroup> page = userGroupRepo.findAll(booleanBuilder, pageable);
		List<UserGroupDto> content = UserGroupProjector.toSearchDto(page.getContent());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

}
