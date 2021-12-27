package com.codest.cities.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.util.StringUtils;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String role = userDetails.getAuthorities().iterator().next().getAuthority();
		
		Cookie userCookie = createCookie("user", StringUtils.capitalize(userDetails.getUsername()));
		Cookie cookieEdit = new Cookie("hasEdit", String.valueOf("ROLE_ALLOW_EDIT".equals(role)));
		
		response.addCookie(userCookie);
		response.addCookie(cookieEdit);
		
		response.sendRedirect("/");
	}
	
	private Cookie createCookie(String name, String value) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(-1);
		
		return cookie;
	}
}
