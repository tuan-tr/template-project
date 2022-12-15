package com.tth.baseproject.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tth.baseproject.dto.user.UserCreateInput;
import com.tth.baseproject.dto.user.UserDto;
import com.tth.baseproject.dto.user.UserUpdateInput;
import com.tth.baseproject.exception.ErrorCode;
import com.tth.baseproject.projector.UserProjector;
import com.tth.common.exception.BadBusinessRequestException;
import com.tth.common.exception.DataNotFoundException;
import com.tth.persistence.constant.UserStatus;
import com.tth.persistence.entity.User;
import com.tth.persistence.provider.filter.UserFilter;
import com.tth.persistence.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserService {

	private UserRepository userRepo;

	public User getEntityOrThrowBadRequest(String id) {
		return userRepo.findById(id)
				.orElseThrow(() -> new BadBusinessRequestException(ErrorCode.USER_NOT_FOUND.name(),
						Map.of("id", id)));
	}

	@Transactional
	public UserDto create(UserCreateInput input) {
		User entity = User.builder()
				.status(UserStatus.ACTIVE)
				.name(input.getName())
				.effectiveFrom(input.getEffectiveFrom())
				.effectiveTo(input.getEffectiveTo())
				.build();
		
		userRepo.save(entity);
		
		return UserDto.builder()
				.id(entity.getId())
				.build();
	}

	@Transactional
	public void update(String id, UserUpdateInput input) {
		User entity = getEntityOrThrowBadRequest(id);
		entity.setName(input.getName());
		entity.setStatus(input.getStatus());
		entity.setEffectiveFrom(input.getEffectiveFrom());
		entity.setEffectiveTo(input.getEffectiveTo());
	}

	public UserDto getDetail(String id) {
		User entity = userRepo.findById(id)
				.orElseThrow(() -> new DataNotFoundException(ErrorCode.USER_NOT_FOUND.name(),
						Map.of("id", id)));
		
		UserDto dto = UserProjector.toDetailDto(entity);
		return dto;
	}

	public Page<UserDto> searchPaging(UserFilter filter, Pageable pageable) {
		Page<User> page = userRepo.findPaging(filter, pageable);
		List<UserDto> content = UserProjector.toPageDto(page.getContent());
		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

}
