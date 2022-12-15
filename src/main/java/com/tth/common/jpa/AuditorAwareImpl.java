package com.tth.common.jpa;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.tth.common.auth.UserContext;
import com.tth.common.util.SecurityContextUtils;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		return SecurityContextUtils.findCurrent()
				.map(UserContext::getUsername);
	}

}
