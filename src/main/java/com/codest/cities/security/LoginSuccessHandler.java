package com.codest.cities.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {		
		String role = ((UserDetails) authentication.getPrincipal()).getAuthorities().iterator().next().getAuthority();
		
		Cookie cookie = new Cookie("hasEdit", String.valueOf("ROLE_ALLOW_EDIT".equals(role)));
		cookie.setMaxAge(-1);
		
		response.addCookie(cookie);
		response.sendRedirect("/");
	}
}
