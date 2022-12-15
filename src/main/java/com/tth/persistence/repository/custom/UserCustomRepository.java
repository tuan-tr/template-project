package com.tth.persistence.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tth.persistence.entity.User;
import com.tth.persistence.provider.filter.UserFilter;

public interface UserCustomRepository {

	Page<User> findPaging(UserFilter filter, Pageable pageable);

}
