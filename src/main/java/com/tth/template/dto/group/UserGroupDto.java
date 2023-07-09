package com.tth.template.dto.group;

import com.tth.template.dto.AuditDto;
import com.tth.template.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class UserGroupDto extends AuditDto {

	private UserDto user;

	private GroupDto group;

}
