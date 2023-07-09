package com.tth.persistence.provider.filter;

import java.time.OffsetDateTime;
import java.util.Set;

import com.tth.persistence.constant.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserFilter {

	private Set<String> ids;

	private Set<UserStatus> statuses;

	private String name;

	private OffsetDateTime effectiveStart;

	private OffsetDateTime effectiveEnd;

	private OffsetDateTime createdFrom;

	private OffsetDateTime createdTo;

	private String keyword;

}
