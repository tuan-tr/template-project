package com.tth.template.dto.group;

import com.tth.persistence.constant.GroupStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GroupUpdateInput {

	@NotNull
	private GroupStatus status;

	@NotBlank
	private String name;

}
