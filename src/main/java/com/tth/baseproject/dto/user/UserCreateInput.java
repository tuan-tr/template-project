package com.tth.baseproject.dto.user;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotBlank;

import com.tth.common.validation.AfterCurrentTime;
import com.tth.common.validation.ValidRangeDateTime;
import com.tth.persistence.constant.UserStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@ValidRangeDateTime(from = "effectiveFrom", to = "effectiveTo")
public class UserCreateInput {

	private UserStatus status;

	@NotBlank
	private String name;

	@AfterCurrentTime
	private OffsetDateTime effectiveFrom;

	private OffsetDateTime effectiveTo;

}
