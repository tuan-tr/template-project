package com.tth.template.dto.user;

import java.time.OffsetDateTime;

import com.tth.persistence.constant.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {

	private OffsetDateTime createdAt;

	private String createdBy;

	private OffsetDateTime updatedAt;

	private String updatedBy;

	private String id;

	private UserStatus status;

	private String name;

	private OffsetDateTime effectiveFrom;

	private OffsetDateTime effectiveTo;

}
