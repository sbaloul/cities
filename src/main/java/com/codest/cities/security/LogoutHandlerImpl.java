package com.codest.cities.security;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

public class LogoutHandlerImpl implements LogoutHandler {

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		Cookie userCookie = createCookie("user");
		Cookie editCookie = createCookie("hasEdit");

		response.addCookie(userCookie);
		response.addCookie(editCookie);
		
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		
		try {
			response.sendRedirect("/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Cookie createCookie(String name) {
		Cookie cookie = new Cookie(name, null);
		cookie.setMaxAge(0);
		
		return cookie;
	}
}
