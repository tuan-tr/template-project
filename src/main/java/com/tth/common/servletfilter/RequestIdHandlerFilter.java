package com.tth.common.servletfilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tth.common.http.CustomHttpHeaders;
import com.tth.common.util.NanoIdUtils;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class RequestIdHandlerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
	) throws ServletException, IOException {
		String requestId = request.getHeader(CustomHttpHeaders.REQUEST_ID);
		
		if (StringUtils.isBlank(requestId)) {
			requestId = NanoIdUtils.randomNanoId();
		}
		
		response.addHeader(CustomHttpHeaders.REQUEST_ID, requestId);
		ThreadContext.put("requestId", requestId);
		
		filterChain.doFilter(request, response);
	}

}
