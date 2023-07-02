package com.tth.persistence.repository;

import com.tth.persistence.entity.UserGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, String> {

	List<UserGroup> findByGroupIdAndUserIdIn(@NotBlank String groupId, @NotEmpty Collection<String> userIds);

}
