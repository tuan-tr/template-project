package com.tth.template.dto.user;

import com.tth.common.validation.AfterCurrentTime;
import com.tth.common.validation.ValidRangeDateTime;
import com.tth.persistence.constant.UserStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@NoArgsConstructor
@Getter
@ValidRangeDateTime(from = "effectiveFrom", to = "effectiveTo")
public class UserUpdateInput {

	private UserStatus status;

	@NotBlank
	private String name;

	@AfterCurrentTime
	private OffsetDateTime effectiveFrom;

	private OffsetDateTime effectiveTo;

}
