package com.prashanth.restcontroller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import com.prashanth.service.UserDetailsServiceImpl;

import antlr.collections.Enumerator;
import io.jsonwebtoken.ExpiredJwtException;

@Component
@CrossOrigin
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		  // response.addHeader("Access-Control-Allow-Origin", "*");
	        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	        response.addHeader("Access-Control-Max-Age", "3600");
	        response.addHeader("Access-Control-Allow-Credentials", "true");
	        response.addHeader("Access-Control-Allow-Headers", "authorization, content-type, xsrf-token");
	        response.addHeader("Access-Control-Expose-Headers", "xsrf-token");
	       
	 
		final String requestTokenHeader = request.getHeader("Authorization");
		System.out.println("request>>"+requestTokenHeader);
		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		// JSONObject obj = new JSONObject();
		HttpSession session = request.getSession();
		if (requestTokenHeader != null) {
			if (requestTokenHeader.startsWith("Bearer ")) {
				logger.info("requestTokenHeader>>"+requestTokenHeader);
				jwtToken = requestTokenHeader.substring(7).trim();
				try {
					username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				} catch (IllegalArgumentException e) {
					System.out.println("Unable to get JWT Token");
					session.setAttribute("token", "Token Required to Access");
				} catch (ExpiredJwtException e) {
					System.out.println("JWT Token has expired");
					session.setAttribute("token", "Token Expired");
					// obj.put("error", "Token Expired");
					// response.getWriter().write(obj.toJSONString());
				}
			} else {
				logger.error("JWT Token does not begin with Bearer String");
				session.setAttribute("token", "JWT Token does not begin with Bearer String");
			}

		} else {
			session.setAttribute("token", "Token Required in header");
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		 if (request.getMethod().equals("OPTIONS")) {
	            response.setStatus(HttpServletResponse.SC_ACCEPTED);
	            return;
	        }
		chain.doFilter(request, response);
	}

}