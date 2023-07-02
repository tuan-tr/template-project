package com.tth.common.jpa;

import com.tth.common.util.SecurityContextUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		return SecurityContextUtils.findCurrent()
				.map(UserDetails::getUsername);
	}

}
