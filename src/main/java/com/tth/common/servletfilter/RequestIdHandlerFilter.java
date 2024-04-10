package com.tth.common.servletfilter;

import com.fasterxml.uuid.Generators;
import com.tth.common.http.CustomHttpHeaders;
import com.tth.common.util.AppConstant;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class RequestIdHandlerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
	) throws ServletException, IOException {
		String requestId = request.getHeader(CustomHttpHeaders.REQUEST_ID);
		
		if (StringUtils.isBlank(requestId)) {
			requestId = Generators.timeBasedEpochGenerator().generate().toString();
		}
		
		response.addHeader(CustomHttpHeaders.REQUEST_ID, requestId);
		ThreadContext.put(AppConstant.FLOW_ID, requestId);
		
		filterChain.doFilter(request, response);
	}

}
