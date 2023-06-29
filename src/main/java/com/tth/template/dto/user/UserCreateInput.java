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
@ValidRangeDateTime(from = "effectiveStart", to = "effectiveEnd")
public class UserCreateInput {

	private UserStatus status;

	@NotBlank
	private String name;

	@AfterCurrentTime
	private OffsetDateTime effectiveStart;

	private OffsetDateTime effectiveEnd;

}
