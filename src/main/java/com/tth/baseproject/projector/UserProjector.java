package com.tth.baseproject.projector;

import java.util.List;
import java.util.stream.Collectors;

import com.tth.baseproject.dto.user.UserDto;
import com.tth.persistence.entity.User;

public class UserProjector {

	public static UserDto toDetailDto(User entity) {
		return UserDto.builder()
				.createdAt(entity.getCreatedAt())
				.createdBy(entity.getCreatedBy())
				.updatedAt(entity.getUpdatedAt())
				.updatedBy(entity.getUpdatedBy())
				.id(entity.getId())
				.name(entity.getName())
				.status(entity.getStatus())
				.effectiveFrom(entity.getEffectiveFrom())
				.effectiveTo(entity.getEffectiveTo())
				.build();
	}

	public static List<UserDto> toPageDto(List<User> entities) {
		return entities.stream()
				.map(e -> toPageDto(e))
				.collect(Collectors.toList());
	}

	public static UserDto toPageDto(User entity) {
		return UserDto.builder()
				.createdAt(entity.getCreatedAt())
				.createdBy(entity.getCreatedBy())
				.id(entity.getId())
				.name(entity.getName())
				.status(entity.getStatus())
				.effectiveFrom(entity.getEffectiveFrom())
				.effectiveTo(entity.getEffectiveTo())
				.build();
	}

}
