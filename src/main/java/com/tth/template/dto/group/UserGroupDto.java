package com.tth.template.dto.group;

import com.tth.template.dto.user.UserDto;
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
public class UserGroupDto {

	private UserDto user;

	private GroupDto group;

}
