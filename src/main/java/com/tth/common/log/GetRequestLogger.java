package com.tth.common.log;

import com.tth.common.jackson.JsonParserProvider;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@AllArgsConstructor
@Log4j2
public class GetRequestLogger implements HandlerInterceptor {
	
	private JsonParserProvider jsonParserProvider;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler
	) throws Exception {
		if (request.getDispatcherType().name().equals(DispatcherType.REQUEST.name())
				&& request.getMethod().equals(HttpMethod.GET.name())
		) {
			Map<String, Object> requestData = new LinkedHashMap<>();
			requestData.put("sessionId", request.getSession().getId());
			requestData.put("method", request.getMethod());
			requestData.put("uri", request.getRequestURI());
			log.debug(jsonParserProvider.toString(requestData));
		}
		return true;
	}
	
}
