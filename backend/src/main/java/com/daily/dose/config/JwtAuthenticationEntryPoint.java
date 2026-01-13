package com.daily.dose.config;
import java.io.IOException;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	public void commence(HttpServletRequest request,HttpServletResponse response,AuthenticationException authException)
	throws IOException{
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.getWriter().write("{\"error\":\"Unauthorized\"}");
		
	}

}
