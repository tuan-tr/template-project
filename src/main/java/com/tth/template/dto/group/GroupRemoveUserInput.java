package com.tth.template.dto.group;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Getter
public class GroupRemoveUserInput {

	@NotEmpty
	private Set<String> userIds;

}
