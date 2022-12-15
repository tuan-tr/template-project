package com.tth.baseproject.service.context;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.tth.common.auth.UserContext;

@Service
public class UserContextService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) {
		return UserContext.builder()
				.username(username)
				.password("")
				.accountNonExpired(true)
				.accountNonLocked(true)
				.credentialsNonExpired(true)
				.enabled(true)
				.build();
	}

}
