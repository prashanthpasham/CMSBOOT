package com.prashanth.restcontroller;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	private static final long serialVersionUID = -7858869558953243875L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		HttpSession session = request.getSession();
		String result = "Unauthorized";
		if (session!=null && session.getAttribute("token") != null) {
			result = session.getAttribute("token").toString();
			session.removeAttribute("token");
		}
		System.out.println("result>>"+result);
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, result);

	}
}