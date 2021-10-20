package com.geekbrains.geekmarketwinter.config;

import com.geekbrains.geekmarketwinter.entites.User;
import com.geekbrains.geekmarketwinter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler
		implements AuthenticationSuccessHandler {
    private UserService userService;
	private HttpSession session;

	public HttpSession getSession() {
		return session;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String userName = authentication.getName();
		User theUser = userService.findByUserName(userName);
		session = request.getSession();
		session.setAttribute("user", theUser);
		System.out.println("user in session");
		if(!request.getHeader("referer").contains("login")) {
			response.sendRedirect(request.getHeader("referer"));
		} else {
			response.sendRedirect(request.getContextPath() + "/shop");
		}
	}
}
